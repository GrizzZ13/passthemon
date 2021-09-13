import * as React from 'react';
import {GiftedChat, Message, Bubble} from "react-native-gifted-chat";
import {DeviceEventEmitter, StyleSheet} from 'react-native';
import App from "../../../App";
import {getChatHistory, initChatHistory} from "../../service/ChatService";
import NavigationService from "../../service/NavigationService";
import BackHeader from "../../common/BackHeader";
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";

const MESSAGE_CHAT = 2;

export class ChatDetail extends React.Component{
    constructor(props) {
        super(props);
        console.log("chat detail constructs");
        let params = this.props.route.params.channelInfo;
        console.log(params)
        if (params.channelId===null || params.channelId===undefined || params.channelId < 0) {
            this.state = {
                channelId: -1,
                messages : [],
                counts: 0,
                pageNumber: 0,
                loading: false,
            }
        }
        else {
            this.state = {
                channelId: params.channelId,
                messages : [],
                counts: 0,
                pageNumber: 0,
                loading: false,
            }
        }
        console.log(params.authorUsername);
        console.log(params.recipientUsername);
        this.user = {
            _id: params.authorId,
            name: params.authorUsername,
        }
        this.other = {
            _id: params.recipientId,
            name: params.recipientUsername,
        }
    }

    data2messages = (data) => {
        let messages = [];
        let message = new Message();
        message._id = Date.now();
        message.text = data.content;
        message.createdAt = data.date;
        message.user = this.other;
        messages.push(message);
        return messages;
    }

    data2messages_2 = (data) => {
        let message = new Message();
        message._id = data.date;
        message.text = data.content;
        message.createdAt = data.date;
        if(this.user._id === data.author)
            message.user = this.user;
        else
            message.user = this.other;
        return message;
    }

    checkChannel = () => {
        console.log("ChatDetail - check channel");
        if(this.state.channelId < 0){
            let data = {
                channelId : -1,
                author : this.user._id,
                recipient: this.other._id,
                content : null,
            }
            App.ws.send(JSON.stringify(data));
        }
    }

    initHistory = () => {
        console.log("ChatDetail - initChatHistory");
        let data = {
            author : this.user._id,
            recipient: this.other._id,
        }
        initChatHistory(data, this.initHistoryCallback);
    }

    componentDidMount() {
        this.listener1 = DeviceEventEmitter.addListener("onmessage", data => {
            console.log(data);
            if (data.author !== this.other._id) return;
            let messages = this.data2messages(data);
            this.setState(previousState => ({
                messages: GiftedChat.append(previousState.messages, messages),
                counts: previousState.counts + 1,
            }))
        })
        this.listener2 = DeviceEventEmitter.addListener("channel", data => {
            if (data.recipient !== this.other._id) return;
            this.setState({
                channelId : data.channelId,
            })
        })
        this.initHistory();
        if(this.state.channelId < 0) {
            console.log("channel id < 0");
            this.checkChannel();
        }
    }

    onSend = (messages = []) => {
        let message = messages[0];
        let data = {
            channelId : this.state.channelId,
            author : this.user._id,
            recipient: this.other._id,
            content : message.text,
            messageType : MESSAGE_CHAT,
        }
        App.ws.send(JSON.stringify(data));
        this.setState(previousState => ({
            messages: GiftedChat.append(previousState.messages, messages),
            counts: previousState.counts + 1,
        }))
    }

    _renderBubble(props) {
        return (
            <Bubble
                {...props}
                wrapperStyle={{
                    left: { //对方的气泡
                        backgroundColor: '#F5F7F7',
                    },
                    right: { //我方的气泡
                        backgroundColor: '#3878ff'
                    }
                }}
            />
        );
    }

    componentWillUnmount(){
        console.log("chat detail will unmount");
        this.listener1.remove();
        this.listener2.remove();
    }

    styles = StyleSheet.create({
        input: {
            height: 24,
            fontSize: 16,
            paddingBottom: 4,
            marginTop: 4,
            borderWidth: 2,
            borderColor: '#dddddd',
            borderRadius: 16,
        }
    });

    loadHistoryCallback = (data) => {
        this.setState({loading: false});
        let chatList = data.list;
        if(chatList.length === 0){
            console.log("empty chat list");
        }
        else{
            this.setState(previousSate => ({
                pageNumber: previousSate.pageNumber + 1,
            }))
            let messages = [];
            for (let i in chatList){
                messages.push(this.data2messages_2(chatList[i]));
            }
            this.setState(previousState => ({
                messages: GiftedChat.append(messages, previousState.messages),
            }))
        }
    }

    initHistoryCallback = (data) => {
        let chatList = data.list;
        if(chatList.length === 0){
            console.log("empty chat list");
        }
        else{
            let messages = [];
            for (let i in chatList){
                messages.push(this.data2messages_2(chatList[i]));
            }
            this.setState(previousState => ({
                messages: GiftedChat.append(messages, previousState.messages),
            }))
        }
    }

    onLoadEarlier = () => {
        this.setState({loading: true});
        setTimeout(()=>{
            if(this.state.loading === true) {
                this.setState({loading: false});
                alert("请稍后再试！");
            }
        }, 4000);
        let data = {
            author: this.user._id,
            recipient: this.other._id,
            pageNumber: this.state.pageNumber,
        }
        getChatHistory(data, this.loadHistoryCallback);
    }

    pressAvatar = (user) => {
        if(user._id === this.user._id)
            NavigationService.navigate('Mine')
        else
            NavigationService.navigate('Other',{userid: user._id})
    }

    render(){
         return(
             <NativeBaseProvider>
                 <BackHeader text={this.other.name}/>
                 <GiftedChat
                     messages={this.state.messages}
                     onSend={messages => this.onSend(messages)}
                     user={this.user}
                     showAvatarForEveryMessage={true}
                     showUserAvatar
                     alwaysShowSend
                     renderAvatarOnTop={true}
                     alignTop={true}
                     loadEarlier={true}
                     onLoadEarlier={this.onLoadEarlier}
                     isLoadingEarlier={this.state.loading}
                     textInputStyle={this.styles.input}
                     renderBubble={this._renderBubble}
                     onPressAvatar={this.pressAvatar}
                 />
             </NativeBaseProvider>
         )
    }
}
