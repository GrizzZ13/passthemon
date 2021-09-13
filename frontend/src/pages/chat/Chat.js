import React from 'react';
import {Text, DeviceEventEmitter, TouchableOpacity, Image} from "react-native";
import Spinner from "react-native-loading-spinner-overlay";
import {Box, FlatList,NativeBaseProvider,View,HStack} from "native-base";
import CommonHead from "../../common/CommonHead";
import {getChannelLists} from "../../service/ChatService";
import storage from "../../config/Storage";
import {ChatListItem} from "./ChatListItem";
import NavigationService from "../../service/NavigationService";
import {styles} from "../../common/ListItem";
import {checkStatusCode} from "../../service/NotificationService";
export function ChatScreen({navigation}) {
    return (
        <NativeBaseProvider>
            <ChatList navigation={navigation} />
        </NativeBaseProvider>
    );
}

class ChatList extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            channels: [],
            refreshing: false,
            loadingState: true,
            haveNotice:false,
        };
    }

    componentWillUnmount() {
        this.listener.remove();
        console.log("Chat will unmount");
        this.timer && clearInterval(this.timer);
    }

    callback = data => {
        this.setState({
            channels: data.list,
            loadingState: false,
            refreshing: false,
        });
    };

    checkNewNotification=()=>{
        console.warn("time out")
        //如果已经标识小红点则不在继续发请求
        if(this.state.haveNotice) return;
        storage.load({key:'statusCode'}).then(
            ret=>{
                const params={
                    name:"STATUS_CODE",
                    value:ret.value,
                };
                const callback=(data)=>{
                    if(data.flag){
                        this.setState({
                            haveNotice:false,
                        });
                    }else {
                        this.setState({haveNotice:true});
                    }
                }
                checkStatusCode(params,callback);
            }
        ).catch(err=>{
            if(err.name==="NotFoundError"){
                const params={
                    name:"STATUS_CODE",
                    value: null,
                };
                const callback=(data)=>{
                    if(data.flag){
                        this.setState({
                            haveNotice:false,
                        });
                    }
                    else {
                        this.setState({haveNotice:true});
                    }
                }
                checkStatusCode(params,callback);
            }
        })
    }

    _onListRefresh = () => {
        setTimeout(() => {
            this.setState({
                refreshing: false,
            }); //停止刷新
        }, 3000);
        this.setState({
            refreshing: true,
        })
        let param = {
            userId: this.state.userId,
        }
        getChannelLists(param, this.callback);
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
            let json = {userId: ret.userid};
            getChannelLists(json, this.callback);
        });

        this.listener = DeviceEventEmitter.addListener("onmessage", data => {
            let json = {userId: this.state.userId};
            getChannelLists(json, this.callback);
        });

        this.checkNewNotification();
        //设置定时器
        this.timer=setInterval(this.checkNewNotification,60*1000*10);
    }

    ChannelCard({navigation}, item){
        const onPress = () => {
            navigation.navigate('ChatInside', {channelInfo: item,})
        }
        return <ChatListItem
            username={item.recipientUsername}
            image={item.avatar}
            id={item.recipientId}
            onPress={onPress}
        />
    }

    renderTitleItem() {
        return(
            <HStack>
                <Text style={
                    {
                        fontSize: 18,
                        color: '#555555',
                        height: 40,
                        textAlign:'center',
                        justifyContent: 'center',
                        marginTop:15,
                        marginLeft:120
                    }}>
                    消息列表
                </Text>
                <TouchableOpacity
                    onPress={()=>{
                        this.setState({haveNotice:false})
                        NavigationService.navigate("Notification")}
                    }
                    style={{
                        marginLeft:100
                    }}>
                    {
                        this.state.haveNotice ?
                            <Image
                                source={require('./haveNotification.png')}
                                alt={' '}
                                style={{
                                    width: 40,
                                    height: 40,
                                    marginTop: 10,
                                }}
                            />
                            :
                            <Image
                                source={require('./notification.png')}
                                alt={' '}
                                style={{
                                    width: 40,
                                    height: 40,
                                    marginTop: 4,
                                }}
                            />
                    }
                </TouchableOpacity>
            </HStack>
        )
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


    render(){
        const {navigation} = this.props;
        return (
            <NativeBaseProvider>
                <Box>
                    <CommonHead
                        titleItem={() => this.renderTitleItem()}
                    />
                </Box>
                <FlatList
                    data={this.state.channels}
                    mx="1%"
                    ListEmptyComponent={this.createEmptyView()}
                    refreshing={this.state.refreshing}
                    onRefresh={this._onListRefresh}
                    onEndReachedThreshold={0.3}
                    renderItem={({item}) => (
                        <Box borderRadius="md" mt={1} backgroundColor="white">
                            {this.ChannelCard({navigation}, item)}
                        </Box>
                    )}
                    keyExtractor={item => item.channelId}
                />
            </NativeBaseProvider>
        );
    }
}

