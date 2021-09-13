import * as React from 'react';
import {getOrdersByUserId} from '../../service/OrderService';
import GroupPurchaseCell from "../../components/GroupPurchaseScene";
import  {
    NativeBaseProvider,
    Text,
    FlatList,
} from 'native-base';
import {ActivityIndicator, StyleSheet} from 'react-native';
import {getAllImgForThisPage} from "../../service/ImageService";
export class OtherComments extends React.Component{
    constructor() {
        super();
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
    }
    componentDidMount() {
        this.setState({
            userid:this.props.route.params.userid,
            opt:this.props.route.params.opt,
        });
        this.setState({
            fetchPage: 0,
        }); //开始刷新
        const params={
            userId:this.props.route.params.userid,
            fetchPage:0,
            startTime:null,
            endTime:null,
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
    }

    fetchImages(goodsList){
        console.log("goodsList");
        console.log(goodsList);
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
            console.log(data);
            this.setState({
                imagesList: this.state.imagesList.concat(data.list),
            })
        }
        getAllImgForThisPage(json, callback);
    }

    footerLoading = ()=>{
        if(this.state.isEnd) return (<ActivityIndicator size="large" color="#0000ff"/>);
        else {
            return (<Text style={{fontSize: 16, color : '#d0d0d0', alignSelf: 'center'}}>
                您已浏览所有订单！
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
            startTime:null,
            endTime:null,
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
    _getOrdersByUserId = () => {
        const params={
            userId:this.state.userid,
            fetchPage:this.state.fetchPage,
            startTime:null,
            endTime:null,
        };
        const callback=(data)=>{
            if(data.length===0){
                this.setState({isEnd:true});
            }
            else{
                const orders = [...this.state.ordersInfo ];
                for(let i=0;i<data.orders.length;i++){
                    orders.push(data.orders[i]);
                }
                //console.warn(orders.length);
                this.setState({
                    fetchPage:this.state.fetchPage+1,
                    ordersInfo:orders,
                })
            }
        }
        getOrdersByUserId(params,callback,this.state.opt);
    }
    render(){
        return(
            <NativeBaseProvider>
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
    searchIcon: {
        alignSelf:'flex-start',
        marginTop:"6%",
        paddingLeft:"8%",
        height: 24,
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
        alignSelf:'flex-start',
        paddingLeft:"9%",
        marginTop:"3%",
        height: 36,
    },
});
