import React from 'react';
import {
    Box,
    HStack,
    Image, Pressable,
    View,
    VStack,
    Center
} from 'native-base';
import {deleteAllHistory, listHistory, listHistoryByTime} from '../service/HistoryService';
import {NativeBaseProvider} from 'native-base/src/core/NativeBaseProvider';
import {
    ActivityIndicator,
    Dimensions,
    StyleSheet,
    SectionList,
    TouchableOpacity,
    Text,
} from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import storage from '../config/Storage';
import CommonHead from "../common/CommonHead";
import DateRangePicker from './DateRangePicker';
import {getAllImgForThisPage} from "../service/ImageService";

const dimension = Dimensions.get('window')
const itemWidth = 0.32 * dimension.width;
const cols = 3;
const cMargin = (dimension.width - (itemWidth * cols)) / 6.0;
const rMargin = 3;

export class HistoryList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            history: [],
            imagesList: [],
            fetchPage: 0,
            refreshing: false,
            isEnd: false,
            loadingState: true,
            isTimeSelecting: false,
            isEndReached:false,
        };
    }

    componentDidMount() {
        storage.load({key: 'loginState'}).then(ret => {
            setTimeout(() => {
                this.setState({
                    loadingState: false,
                }); //停止刷新
            }, 3000);
            this.setState({
                userId: ret.userid,
            });
            const callback = data => {
                if (data.list.length === 0 || data.list[0].data.length < 12) {
                    this.setState({isEnd: true});
                } else {
                    this.setState({isEnd: false});
                }
                this.setState({
                    history: data.list,
                    loadingState: false,
                    refreshing: false,
                });
                this.fetchImages();
            };
            let json = {userId: ret.userid, fetchPage: 0};
            listHistory(json, callback);
        });
    }

    styles = StyleSheet.create({
        bookImage: {
            justifyContent: 'center',
            width: itemWidth,
            height: 1.1 * itemWidth,
        },
        bookNameText: {
            fontSize: 17,
            fontWeight: 'bold',
        },
        historyTitle: {
            fontSize: 18,
            height: 40,
            textAlign:'center',
            justifyContent: 'center',
            paddingTop: "2.3%",
        },
        calendarIcon: {
            alignSelf:'flex-start',
            height: 32,
            width: 24,
            marginLeft: 3,
        },
        sectionListStyle: {
            flexDirection:'row',
            flexWrap:'wrap',
            alignItems:'flex-start',
        },
        cellStyle: {
            alignItems:'center',
            borderRadius: 5,
            marginLeft: cMargin,
            marginRight: cMargin,
            marginTop:rMargin,
            marginBottom:rMargin,
            width:itemWidth
        },
        sectionStyle: {
            width: dimension.width,
            padding: 4,
            color:'#fff',
        },
        titleStyle: {
            textAlign: 'center',
            fontSize: 16,
        },
        headTopImg: {
            width: 15,
            height: 15,
            marginRight: 5,
        },
        topBtnStyle: {
            flexDirection: 'row',
            justifyContent: 'center',
            alignItems: 'center',
            width: 70,
            height: 30,
            borderColor: '#e6e6e6',
            borderWidth: 1,
            borderRadius: 20,
        },
        headTopText: {
            fontSize: 14,
            color: '#515151',
        },
        datePicker: {
            borderColor: '#87ceeb',
            borderWidth: 1.5,
            borderRadius: 6,
            alignSelf:'center',
            width: "100%",
            marginBottom: '2%'
        },
        selectedDateContainerStyle: {
            height: 36,
            width: "80%",
            alignItems: "center",
            justifyContent: "center",
            backgroundColor: "#fa8072",
            borderRadius: 16,
        },
        selectedDateStyle: {
            fontWeight: "bold",
            color: "#fff5ee",
        },
    });

    fetchImages(){
        let list = [];
        for(let i = 0; i < this.state.history.length; i++){
            let length = this.state.history[i].data.length;
            for(let j = 0; j < length; j++){
                let tmp = {
                    data: this.state.history[i].data[j].id
                }
                list.push(tmp);
            }
        }
        let json = {
            list: list,
        }

        const callback = data =>{
            this.setState({
                isEndReached: true,
                imagesList: this.state.imagesList.concat(data.list),
            })
        }
        getAllImgForThisPage(json, callback);
    }

    HistoryCard({navigation}, item) {
        for(let i = 0; i < this.state.imagesList.length; i++){
            if(item.id === this.state.imagesList[i].goodsId){
                item.image = this.state.imagesList[i].image;
                break;
            }
        }
        return (
            <TouchableOpacity
                onPress={() =>
                    navigation.navigate('HomeInside', {
                        goodsInfo: item,
                    })
                }>
                <Box height={1.52*itemWidth}>
                    <VStack space={2}>
                        <Box>
                            {item.image === null ?
                                <Image
                                    style={this.styles.bookImage}
                                    alt="goodsImage"
                                    source={require('../asset/img/preGoodsImage.png')}
                                />
                                :<Image
                                    style={this.styles.bookImage}
                                    alt="goodsImage"
                                    source={{uri: 'data:image/jpg;base64,' + item.image}}
                                />
                            }
                        </Box>
                        <Box mb={2}>
                            <Center height={0.32*itemWidth}>
                                <Text
                                    style={{
                                        fontSize: 14,
                                        textAlign: 'center',
                                        overflow: 'hidden',
                                    }}
                                    numberOfLines={2}
                                    ellipsizeMode={'tail'}>
                                    {item.name}
                                </Text>
                            </Center>
                        </Box>
                    </VStack>
                </Box>
            </TouchableOpacity>
        );
    }

    createEmptyView() {
        return (
            <Spinner
                visible={this.state.loadingState}
                textContent={'Loading...'}
                textStyle={{
                    color: '#FFF',
                }}
            />
        );
    }


    footerLoading = () =>
        this.state.isEnd === false ? (
            <ActivityIndicator size="large" color="#0000ff" />
        ) : (
            <Center paddingX={100}>
                <Text style={{fontSize: 16, color: '#a0a0a0', alignSelf: 'center'}}>您已浏览所有物品</Text>
            </Center>
        );

    delete= () =>{
        const callback = data => {
            console.log(data);
            this.setState({
                history: [],
                isEnd: true,
            })
        };
        let json = {
            userId: this.state.userId,
        };
        console.log(this.state.userId);
        deleteAllHistory(json, callback);

    }

    renderLeftItem() {
        return(
            <Box ml={2}>
                <TouchableOpacity
                    style={this.styles.topBtnStyle}
                    activeOpacity={0.9}
                    onPress={() => {
                        this.props.navigation.goBack();
                    }}>
                    <Image
                        source={require('../asset/img/left_arrow.png')}
                        style={this.styles.headTopImg}
                        resizeMode={'contain'}
                        alt={' '}
                    />
                    <Text style={this.styles.headTopText}>返回</Text>
                </TouchableOpacity>
            </Box>
        )
    }

    renderTitleItem() {
        return(
            <HStack>
                <Text style={this.styles.historyTitle}>浏览记录</Text>
                <Center>
                    <Pressable style={this.styles.calendarIcon}
                               onPress={() => {
                                   this.setState({
                                       isTimeSelecting: !this.state.isTimeSelecting
                                   })
                               }}
                    >
                        <Image
                            style= {this.styles.calendarIcon}
                            alt="calendar"
                            source={require('../asset/img/icon-calendar.png')}/>
                    </Pressable>
                </Center>
            </HStack>
        )
    }

    renderRightItem() {
        return(
            <Box mr={2}>
                <TouchableOpacity
                    style={this.styles.topBtnStyle}
                    activeOpacity={0.9}
                    onPress={() => {this.delete()}}>
                    <Text style={this.styles.headTopText}>清空</Text>
                </TouchableOpacity>
            </Box>
        )
    }


    render() {
        const {navigation} = this.props;
        return (
            <NativeBaseProvider>
                <Box>
                    <CommonHead
                        leftItem={() => this.renderLeftItem()}
                        titleItem={() => this.renderTitleItem()}
                        rightItem={() => this.renderRightItem()}
                    />
                </Box>

                <Box style={[this.styles.datePicker, {display: this.state.isTimeSelecting ? "flex" : "none"}]} >
                    <DateRangePicker
                        onSuccess={(s, e) => {
                            console.log(s + '||' + e);
                            this.setState({
                                isEnd: false,
                            });
                            // let startTimeSubstring = " 00:00:00";
                            let startDate = s + " 00:00:00";
                            // let endTimeSubstring = " 23:59:59";
                            let endDate = e + " 23:59:59";
                            // console.log(startDate + '||' + endDate);
                            const callback=data=>{
                                console.log(data);
                                this.setState({
                                    fetchPage: 0,
                                    history: data.list,
                                });

                                if (data.list.length < 15) {
                                    this.setState({
                                        isEnd: true,
                                    });
                                }
                                else {
                                    this.fetchImages();
                                }
                            }
                            let json = {
                                userId: this.state.userId,
                                fetchPage: 0,
                                startTime: startDate,
                                endTime: endDate,
                            }
                            listHistoryByTime(json, callback);
                        }}
                        theme={{ markColor: '#f74507', markTextColor: 'white' }}
                    />
                </Box>
                <SectionList
                    sections={this.state.history}
                    ListEmptyComponent={this.createEmptyView()}
                    ListFooterComponent={this.footerLoading()} //添加尾部组件
                    onEndReachedThreshold={0.2}
                    onEndReached={() =>{
                        if (this.state.isEnd === false && this.state.isEndReached === true) {
                            const callback = data => {
                                if (data.list.length === 0) {
                                    this.setState({
                                        isEnd: true,
                                    });
                                }
                                else {
                                    console.log(data.list);
                                    let lastIndex = this.state.history.length - 1;
                                    if (data.list[0].title === this.state.history[lastIndex].title) {
                                        let newHistory = this.state.history;
                                        newHistory[lastIndex].data = this.state.history[lastIndex].data.concat(data.list[0].data);
                                        data.list.splice(0, 1);
                                        console.log("finish");
                                        if (data.list.length === 0) {
                                            console.log(newHistory);
                                            this.setState({
                                                fetchPage: this.state.fetchPage + 1,
                                                history: newHistory,
                                            });
                                        } else {
                                            this.setState({
                                                fetchPage: this.state.fetchPage + 1,
                                                history: newHistory.concat(data.list),
                                            });
                                        }
                                    } else {
                                        this.setState({
                                            fetchPage: this.state.fetchPage + 1,
                                            history: this.state.history.concat(data.list),
                                        });
                                    }
                                    this.fetchImages();
                                }
                            };

                            let json = {
                                userId: this.state.userId,
                                fetchPage: this.state.fetchPage + 1,
                            };
                            listHistory(json, callback);
                        }
                    }
                    }
                    contentContainerStyle={this.styles.sectionListStyle}//设置cell的样式
                    pageSize={4}
                    renderItem={({item}) => (
                        <View style={this.styles.cellStyle}>
                            <Box borderRadius="md" backgroundColor="white" shadow={3}>
                                {this.HistoryCard({navigation}, item)}
                            </Box>
                        </View>
                    )}
                    renderSectionHeader={({ section: { title } }) => (
                        <Box
                            style={this.styles.sectionStyle}
                        >
                            <Text style={this.styles.titleStyle}>
                                {title}
                            </Text>
                        </Box>
                    )}
                />
            </NativeBaseProvider>
        );
    }
}
