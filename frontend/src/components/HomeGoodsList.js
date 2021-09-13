import React from 'react';
import {
    Box,
    FlatList,
    NativeBaseProvider,
    Image,
    VStack,
    HStack,
    View,
    Pressable,
    Select,
    CheckIcon,
} from 'native-base';
import {getMaxGoodsPage, listGoodsByPage, listMyGoodsByPage} from '../service/GoodsService';
import {
    StyleSheet,
    Text,
    ActivityIndicator,
} from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import storage from "../config/Storage";
import {getAllImgForThisPage} from "../service/ImageService";
import {BEING_AUDITED, IMAGE_FAILED_AUDIT, ON_SALE, TEXT_FAILED_AUDIT} from "../config/AuditConstant";
export class HomeGoodsList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            goodsData: [],
            imagesList: [],
            fetchPage: 0,
            maxPage: 0,
            refreshing: false,
            isEnd: false,
            loadingState: true,
            userId: 0,
            isTimeOut: false,
            pageNum: 0,
            category: 0,
            attrition: 0,
        };
    }

    myCallback = data => {
        if (data.list.length === 0) {
            this.setState({isEnd: true});
        } else {
            this.setState({isEnd: false});
        }
        this.setState({
            loadingState: false,
            refreshing: false,
            goodsData: data.list,
            pageNum: this.state.pageNum + 1,
        });
        this.fetchImages(data.list);
    };

    callback = data => {
        if (data.list.length < 8) {
            this.setState({isEnd: true});
        } else {
            this.setState({isEnd: false});
        }
        this.setState({
            loadingState: false,
            refreshing: false,
            goodsData: data.list,
            fetchPage: data.currentPage,
            maxPage: data.maxPage,
            pageNum: this.state.pageNum + 1,
        });
        this.fetchImages(data.list);
    };

    fetchImages(goodsList){
        let list = [];
        for(let i = 0; i < goodsList.length; i++){
            let tmp = {
                data: goodsList[i].id
            }
            list.push(tmp);
        }
        let json = {
            list: list,
        }
        const callback = data =>{
            this.setState({
                imagesList: this.state.imagesList.concat(data.list),
            })
        }
        getAllImgForThisPage(json, callback);
    }

    fetchGoods=()=>{
        if(this.props.fromView === "Mine") {
            let json = {
                fetchPage: this.state.fetchPage,
                userId: this.props.userid,
            };
            listMyGoodsByPage(json, this.myCallback);
            this.setState({
                userId: this.props.userid,
            });
        }
        else{
            storage.load({key: 'loginState'}).then(ret => {
                const maxPageCallback = json =>{
                    let data = json.data;
                    let page = Math.floor(Math.random() * data);
                    let json_2 = {
                        fetchPage: page,
                        userId: ret.userid,
                        category: parseInt(this.state.category),
                        attrition: parseInt(this.state.attrition)
                    };
                    listGoodsByPage(json_2, this.callback);
                }
                getMaxGoodsPage({
                    category: parseInt(this.state.category),
                    attrition: parseInt(this.state.attrition)
                }, maxPageCallback);
                this.setState({
                    userId: ret.userid,
                });
            });
        }
    }

    componentDidMount() {
        setTimeout(() => {
            this.setState({
                loadingState: false,
                refreshing: false,
            }); //停止刷新
        }, 3000);
        this.fetchGoods();
    }

    onListRefresh = () => {
        this.setState({
            refreshing: true,
            fetchPage: 0,
            imagesList:[]
        }); //开始刷新

        this.fetchGoods();
    };

    styles = StyleSheet.create({
        priceText: {
            fontSize: 17,
            color: '#ec655b',
        },
        goodsNameText: {
            fontSize: 18,
            overflow: 'hidden',
        },
        loadingContainer: {
            flex: 1,
            justifyContent: 'center',
        },
    });

    renderState=(view,wantNum,State)=>{
        if(view==='Mine'){
            switch (State) {
                case ON_SALE:
                    return (
                        <Box pl={"10%"}>
                            <Text>{wantNum}人想要</Text>
                        </Box>
                    );
                case BEING_AUDITED:
                    return (
                        <Box pl={"10%"}>
                            <Text style={{color:'orange'}}>正在审核</Text>
                        </Box>
                    );
                case IMAGE_FAILED_AUDIT:
                    return (
                        <Box pl={"10%"} ml={'-5'}>
                            <Text style={{color:'red'}}>审核未通过</Text>
                        </Box>
                    );
                case TEXT_FAILED_AUDIT:
                    return (
                        <Box pl={"10%"}>
                            <Text>审核未通过</Text>
                        </Box>
                    );
            }
        }else {
            return <Text/>
        }
    }

    GoodsCard({navigation}, item) {
        for(let i = 0; i < this.state.imagesList.length; i++){
            if(item.id === this.state.imagesList[i].goodsId){
                item.image = this.state.imagesList[i].image;
                break;
            }
        }
        return (
            <Pressable
                onPress={() => item.state!==2 ?
                    navigation.navigate('HomeInside', {
                        goodsInfo: item,
                    }) : {}
                }>
                <VStack >
                    <Box>
                        {item.image === null?
                            <Image
                                width={200}
                                height={230}
                                borderTopRadius={5}
                                alt="goodsImage"
                                source={require('../asset/img/preGoodsImage.png')}
                            />
                            :<Image
                                width={200}
                                height={230}
                                borderTopRadius={5}
                                alt="goodsImage"
                                source={{uri: 'data:image/jpg;base64,' + item.image}}
                            />}
                    </Box>
                    <Box px={4} h={8} pt={1}>
                        <Text
                            style={this.styles.goodsNameText}
                            numberOfLines={1}
                            ellipsizeMode={'tail'}
                        >
                            {item.name}
                        </Text>
                    </Box>
                    <Box px={4} pb={3}>
                        <HStack space={1}>
                            <Text style={this.styles.priceText}>{item.price}元</Text>
                            {this.renderState(this.props.fromView,item.wanterNum,item.state)}
                        </HStack>
                    </Box>
                </VStack>
            </Pressable>
        );
    }

    setCategory = itemValue =>{
        this.setState({
                category: itemValue,
                fetchPage: 0,
                imagesList:[]
            },
            ()=>{
                this.fetchGoods();
            })
    }

    setAttrition = itemValue =>{
        this.setState({
                attrition: itemValue,
                fetchPage: 0,
                imagesList:[]
            },
            ()=>{
                this.fetchGoods();
            })
    }

    footerLoading = () =>
        this.state.isEnd === false ? (
            <ActivityIndicator size="large" color="#0000ff" />
        ) : (
            <Text style={{fontSize: 16, color: '#bbbbbb', alignSelf: 'center'}}>
                哎呀，到底啦！
            </Text>
        );

    Header = () =>{
        if(this.props.fromView !== "Mine") {
            return (
                <HStack>
                    <Select
                        variant="rounded"
                        width={'47%'}
                        ml={'1%'}
                        selectedValue={this.state.category}
                        accessibilityLabel="物品种类"
                        placeholder="物品种类"
                        onValueChange={itemValue => this.setCategory(itemValue)}
                        _selectedItem={{
                            bg: 'cyan.600',
                            endIcon: <CheckIcon size={4} />,
                        }}>
                        <Select.Item label="不限" value="0"/>
                        <Select.Item label="服饰装扮" value="1"/>
                        <Select.Item label="酒水零食" value="2"/>
                        <Select.Item label="电子产品" value="3"/>
                        <Select.Item label="游戏道具" value="4"/>
                        <Select.Item label="纸质资料" value="5"/>
                        <Select.Item label="生活用品" value="6"/>
                        <Select.Item label="其他" value="7"/>
                    </Select>
                    <Select
                        variant="rounded"
                        width={'47%'}
                        ml={'4%'}
                        mr={'1%'}
                        selectedValue={this.state.attrition}
                        accessibilityLabel="崭新程度"
                        placeholder="崭新程度"
                        onValueChange={itemValue => this.setAttrition(itemValue)}
                        _selectedItem={{
                            bg: 'cyan.600',
                            endIcon: <CheckIcon size={4}/>,
                        }}>
                        <Select.Item label="不限" value="0"/>
                        <Select.Item label="全新未用" value="-1"/>
                        <Select.Item label="几乎全新" value="-2"/>
                        <Select.Item label="轻微磨损" value="-3"/>
                        <Select.Item label="中度磨损" value="-4"/>
                        <Select.Item label="其他" value="-5"/>
                    </Select>
                </HStack>
            )
        }
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

    render() {
        const {navigation, fromView} = this.props;
        return (
            <NativeBaseProvider>
                <FlatList
                    data={this.state.goodsData}
                    numColumns={2}
                    mx="1%"
                    ListEmptyComponent={this.createEmptyView()}
                    ListHeaderComponent={this.Header()}
                    ListFooterComponent={this.footerLoading()} //添加尾部组件
                    refreshing={this.state.refreshing}
                    onRefresh={this.onListRefresh}
                    onEndReachedThreshold={0.5}
                    onEndReached={({distanceFromEnd}) =>
                        setTimeout(() => {
                            if (this.state.isEnd === false) {
                                const myCallback = data => {
                                    if (data.list.length === 0) {
                                        this.setState({
                                            isEnd: true,
                                        });
                                    } else {
                                        this.setState({
                                            fetchPage: this.state.fetchPage + 1,
                                            goodsData: this.state.goodsData.concat(data.list),
                                        });
                                        this.fetchImages(data.list);
                                    }
                                };

                                const callback = data => {
                                    if (data.list.length === 0) {
                                        this.setState({
                                            isEnd: true,
                                        });
                                    } else {
                                        this.setState({
                                            fetchPage: data.currentPage,
                                            maxPage: data.maxPage,
                                            goodsData: this.state.goodsData.concat(data.list),
                                            pageNum: this.state.pageNum + 1,
                                        });
                                        this.fetchImages(data.list);
                                        if(this.state.pageNum >= this.state.maxPage){
                                            this.setState({
                                                isEnd: true,
                                            })
                                        }
                                    }
                                };

                                if(this.props.fromView === "Mine"){
                                    let json = {
                                        fetchPage: this.state.fetchPage + 1,
                                        userId: this.state.userId,
                                    };
                                    listMyGoodsByPage(json, myCallback);
                                }
                                else{
                                    let page = this.state.fetchPage + 1;
                                    if(page >= this.state.maxPage){
                                        page = Math.floor(Math.random() * this.state.maxPage);
                                    }
                                    let json = {
                                        fetchPage: page,
                                        userId: this.state.userId,
                                        category: parseInt(this.state.category),
                                        attrition: parseInt(this.state.attrition)
                                    };
                                    listGoodsByPage(json, callback);
                                }
                            }
                        }, 0)
                    }
                    renderItem={({item}) => (
                        <View centerContent width="48%" mx="1%">
                            <Box borderRadius="md" my={1} backgroundColor="white" shadow={3}>
                                {this.GoodsCard({navigation}, item)}
                            </Box>
                        </View>
                    )}
                    keyExtractor={item => item.id}
                />
            </NativeBaseProvider>
        );
    }
}
