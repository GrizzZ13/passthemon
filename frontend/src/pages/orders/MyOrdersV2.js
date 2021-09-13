import * as React from 'react';
import moment from "moment";
import storage from '../../config/Storage';
import {getOrdersByUserId} from '../../service/OrderService';
import {TouchableOpacity} from 'react-native';
import GroupPurchaseCell from "../../components/GroupPurchaseScene";
import DateRangePicker from '../../components/DateRangePicker';
import  {
    Pressable,
    Center,
    NativeBaseProvider,
    Text,
    HStack,
    Box,
    Image,
    FlatList,
} from 'native-base';
import {ActivityIndicator, StyleSheet} from 'react-native';
import {getAllImgForThisPage} from "../../service/ImageService";
import CommonHead from "../../common/CommonHead";
import {listHistoryByTime} from "../../service/HistoryService";
export class MyOrdersV2 extends React.Component{
    constructor(props) {
        super(props);
    }
    state={
        opt:-1,//请求类型, -1代表全部订单, 0代表买家订单, 1代表卖家订单
        userid:0,
        ordersInfo:[],
        imagesList: [],
        isEnd:false,
        fetchPage:0,
        isReceived:[],
        commentTag:[],
        isComment:[],
        comment:[],
        starCount:[],
        isSelectDateRange:false,
        detailSelectDateRange:false,
        selectedRange:{firstDate: null, secondDate: null},
        startTime: 0,
        endTime: 0,
    }
    componentDidMount() {
        storage.load({key: 'loginState'}).then(ret => {
            this.setState(
                {
                    userid:ret.userid,
                    opt:this.props.route.params.opt,
                });
            this.onListRefresh();
        });
    }

    fetchImages(ordersList){
        let list = [];
        for(let i = 0; i < ordersList.length; i++){
            let tmp = {
                data: ordersList[i].goods
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

    footerLoading = ()=>{
        if(this.state.isEnd) return (<ActivityIndicator size="large" color="#0000ff"/>);
        else {
            return (<Text style={{fontSize: 16, color : '#d0d0d0', alignSelf: 'center', marginTop: 15}}>
                您已浏览所有记录！
            </Text>);
        }
    }
    onListRefresh = () => {
        this.setState({
            fetchPage: 0,
        }); //开始刷新
        const params={
            userId:this.state.userid,
            fetchPage:0,
            startTime:this.state.selectedRange.firstDate,
            endTime:this.state.selectedRange.secondDate,
        };
        const callback=(data)=>{
            if(data.length===0){
                this.setState({isEnd:true});
            }
            else{
                this.setState({
                    ordersInfo:data.orders,
                    fetchPage:1,
                })
                this.fetchImages(data.orders);
            }
        }
        getOrdersByUserId(params,callback,this.state.opt);
    };

    _getOrdersByUserIdAnTime = () => {
        const params={
            userId:this.state.userid,
            fetchPage:0,
            startTime:this.state.selectedRange.firstDate,
            endTime:this.state.selectedRange.secondDate,
        };
        const callback=(data)=>{
            if(data.length===0){
                this.setState({isEnd:true});
            }
            else{
                const orders = [...this.state.ordersInfo ];
                //concat莫名奇妙寄了
                for(let i=0;i<data.orders.length;i++){
                    orders.push(data.orders[i]);
                }
                this.setState({
                    fetchPage:1,
                    ordersInfo:orders,
                })
            }
        }
        getOrdersByUserId(params,callback,this.state.opt);
    }

    _getOrdersByUserId = () => {
        const params={
            userId:this.state.userid,
            fetchPage:this.state.fetchPage,
            startTime:this.state.selectedRange.firstDate,
            endTime:this.state.selectedRange.secondDate,
        };
        const callback=(data)=>{
            if(data.length===0){
                this.setState({isEnd:true});
            }
            else{
                const orders = [...this.state.ordersInfo ];
                //concat莫名奇妙寄了
                for(let i=0;i<data.orders.length;i++){
                    orders.push(data.orders[i]);
                }
                this.setState({
                    fetchPage:this.state.fetchPage+1,
                    ordersInfo:orders,
                })
            }
        }
        getOrdersByUserId(params,callback,this.state.opt);
    }

    renderLeft = () => {
        return(
            <Box ml={2}>
                <TouchableOpacity
                    style={styles.topBtnStyle}
                    activeOpacity={0.9}
                    onPress={() => {
                        this.props.navigation.goBack();
                    }}>
                    <Image
                        source={require('../../asset/img/left_arrow.png')}
                        style={styles.headTopImg}
                        resizeMode={'contain'}
                        alt={' '}
                    />
                    <Text style={styles.headTopText}>返回</Text>
                </TouchableOpacity>
            </Box>
        )
    }

    renderTitle = () => {
        return(
            <HStack>
                <Text style={styles.tradeTitle}>
                    交易记录
                </Text>
                <Center>
                    <Pressable
                        style={styles.calendarIcon}
                        onPress={() => {this.setState({detailSelectDateRange:!this.state.detailSelectDateRange});}}
                        onLongPress={() => {
                            this.setState({
                                selectedRange:{firstDate:null,secondDate:null},
                                isSelectDateRange:false,
                            });
                        }}
                    >
                        <Image
                            style={styles.calendarIcon}
                            alt="date"
                            source={require('../../asset/img/icon-calendar.png')}/>
                    </Pressable>
                </Center>
            </HStack>
        )
    }

    renderRight = () => {
        return(
            <Box mr={2}>
                <TouchableOpacity
                    style={styles.topBtnStyle}
                    activeOpacity={0.7}
                    onPress={() => {
                        /* 时间从中午12点算起 */
                        this.setState({
                            isSelectDateRange:true,
                            ordersInfo: [],
                            fetchPage: 0,
                        });
                        this._getOrdersByUserIdAnTime();
                    }}
                >
                    <Text style={styles.headTopText}>
                        筛选
                    </Text>
                </TouchableOpacity>
            </Box>
        )
    }

    render(){
        return(
            <NativeBaseProvider>
                <Box paddingBottom={2}>
                    <CommonHead
                        leftItem={this.renderLeft}
                        titleItem={this.renderTitle}
                        rightItem={this.renderRight}
                    />
                </Box>
                <Box style={[styles.datePicker, {display: this.state.detailSelectDateRange ? "flex" : "none"}]}>
                    <DateRangePicker
                        onSuccess={(s, e) => {
                            console.log(s + '||' + e);
                            let startDate = s + " 00:00:00";
                            let endDate = e + " 23:59:59";
                            let range = {
                                firstDate: startDate,
                                secondDate: endDate
                            }
                            this.setState({
                                selectedRange:range,
                                isEnd: false,
                            });
                        }}
                        theme={{ markColor: '#f74507', markTextColor: 'white' }}
                    />
                </Box>
                <FlatList
                    data={this.state.ordersInfo}
                    numColumns={1}
                    mx="1%"
                    ListFooterComponent={() => this.footerLoading()}
                    refreshing={false}
                    onRefresh={()=>this.onListRefresh()}
                    onEndReachedThreshold={0.3}
                    onEndReached={()=>this._getOrdersByUserId()}
                    renderItem={(item, index) => (
                        <GroupPurchaseCell item={item} opt={this.state.opt} imagesList={this.state.imagesList}/>
                    )}
                />
            </NativeBaseProvider>
        )
    }

}
const styles = StyleSheet.create({
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
    card: {
        margin: 10,
        paddingBottom: 10,
    },
    bookImage: {
        justifyContent: 'center',
        width: "80%",
        height: 288,
        marginBottom: 20,
    },
    orderId: {
        fontSize: 16,
        margin: "2%"
    },
    textArea: {
        marginTop: "2%",
        marginLeft: "5%",
        marginRight: "5%"
    },
    baseText: {
        fontSize: 18,
    },
    money: {
        color: "#dc143c",
        fontSize: 20,
    },
    orderState: {
        borderColor: '#dc143c',
        borderWidth: 1.5,
        borderRadius: 16,
        textAlign: 'center',
        textAlignVertical: 'center',
        width: '36%'
    },
    tradeTitle: {
        fontSize: 17,
        height: 40,
        textAlign:'center',
        justifyContent: 'center',
        paddingTop: "2.3%",
    },
    comment: {
        marginTop: 20,
        backgroundColor: 'white',
        textAlign: 'center',
        borderColor: '#333333',
        borderWidth: 0.5,
        borderRadius: 6,
        width: '90%'
    },
    inputText: {
        alignSelf:'flex-start',
        marginTop:"2%",
        marginLeft: "21%",
        fontSize: 18,
        maxWidth: "40%"
    },
    search: {
        width: 60,
    },
    datePicker: {
        borderColor: '#87ceeb',
        borderWidth: 1.5,
        borderRadius: 6,
        alignSelf:'center',
        width: "80%",
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
    calendarIcon: {
        width: 36,
        height: 36,
    },
});
