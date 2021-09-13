import React from "react";
import {Alert, StyleSheet, TouchableHighlight} from "react-native";
import {Box, Center, Flex, Image, Pressable, VStack, Text} from "native-base";
import storage from "../config/Storage";
import NavigationService from "../service/NavigationService";
import {getUserNameById} from "../service/UserService";
import {removeDemand} from "../service/DemandService";
import UserIcon from "../pages/chat/UserIcon";
import {REMOVE_DEMAND, REMOVE_GOODS} from "../config/UserConstant";
import {DeviceEventEmitter} from "react-native";
import {BEING_AUDITED, IMAGE_FAILED_AUDIT, ON_SALE, TEXT_FAILED_AUDIT} from "../config/AuditConstant";

export class DemandCard extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            readMore: false,
            disable:false,
            textInfo:'删除',
        };
        storage.load({key: 'loginState'}).then(ret => {
            this.setState({
                userId: ret.userid,
            })
        })
    }

    styles = StyleSheet.create({
        priceText: {
            fontSize: 20,
            color: 'red',
        },
        demanderNameText: {
            fontSize: 24,
            fontWeight: 'bold',
        },
        descriptionText:{
            fontSize: 18,
            color : '#555555'
        }
    })
    componentDidMount() {
        if(this.props.item.state===0){
            this.setState({
                disable:true,
                textInfo:'已失效',
            })
        }
    }

    removeMyDemand=()=>{
        this.setState({
            disable:true
        });
        const callback= (data) => {
            let param={
                type:REMOVE_DEMAND,
            }
            DeviceEventEmitter.emit('EditUser', param);
            this.setState(
                {
                    textInfo:'已失效',
                    disable:true,
                }
            )
        };
        const param={
            demandId:this.props.item.id,
        }
        removeDemand(param,callback);
    }
    showConfirm=()=>{
        if(!this.state.disable){
            Alert.alert('提示','确认删除？',[
                {text:'确认',onPress:() => {this.removeMyDemand()}},
                {text:'取消',style:'cancel'}
            ],{cancelable:false});
        }};
    renderState=()=>{
        const {item, navigation} = this.props;
        switch (item.state) {
            case ON_SALE:
                //如果是正常的返回时间
                return(
                    <Box width={'100%'}>
                        <Text style={{color: '#a0a0a0', fontSize: 14}}>
                            {item.upLoadTime}
                        </Text>
                    </Box>

                );
            case BEING_AUDITED:
                return(
                    <Text style={{color:'orange',fontSize: 18}}>正在审核</Text>
                );
            case TEXT_FAILED_AUDIT:
                return (
                    <Text style={{color:'red',fontSize: 18}}>审核未通过</Text>
                );
        }
    }
    renderRightComp=()=>{
        if(this.props.item.uploadUserId!==this.state.userId){
            return(
                <Center paddingRight={3}>
                    <Text
                        style={{fontSize: 16, color: '#777777'}}
                        onPress={()=> {
                            const callback=data=>{
                                console.log(data);
                                let json = {
                                    authorUsername: data.username,
                                    authorId: this.state.userId,
                                    recipientUsername: this.props.item.uploadUsername,
                                    recipientId: this.props.item.uploadUserId,
                                    channelId: null,
                                }
                                console.log(json);
                                NavigationService.navigate('ChatInside', {channelInfo: json});
                            }
                            console.log(this.state.userId)
                            getUserNameById({userId: this.state.userId}, callback);
                        }}
                    >
                        联系ta
                    </Text>
                </Center>
            )}
        else {
            return(
                <Center paddingRight={2}>
                    <VStack mr={'3'}>
                        <Pressable mb={'3'} onPress={()=>{this.props.navigation.navigate('EditDemand',{demandId:this.props.item.id})}}>
                            <Text style={{fontSize: 16, color: '#777777'}}>
                                编辑
                            </Text>
                        </Pressable>
                        <Pressable onPress={this.showConfirm}>
                            <Text style={{fontSize: 16, color: '#777777'}}>
                                {this.state.textInfo}
                            </Text>
                        </Pressable>
                    </VStack>
                </Center>
            )
        }
    }
    render() {
        const {item, navigation} = this.props;
        return(
            <VStack space={2}>
                <Flex flexDirection={'row'} height={20}>
                    <Center flex={1} paddingLeft={2}>
                        <TouchableHighlight
                            onPress={() => {
                                if (item.uploadUserId !== this.state.userId) {
                                    navigation.navigate('Other', {
                                        userid: item.uploadUserId,
                                    })
                                } else {
                                    console.log("item.uploadUserId");
                                    navigation.navigate('Mine')
                                }}
                            }>
                            <UserIcon source={item.uploadUserImage} size={55}/>
                        </TouchableHighlight>
                    </Center>
                    <Flex flex={4} paddingLeft={2}>
                        <Center flex={1}>
                            <Box width={'100%'}>
                                <Text style={{fontSize: 18, color: '#666666'}} ellipsizeMode={'tail'}>
                                    {item.uploadUsername}
                                </Text>
                            </Box>
                        </Center>
                        <Center flex={1}>
                            {this.renderState()}
                        </Center>
                    </Flex>
                    {this.renderRightComp()}
                </Flex>
                <Flex flexDirection={'row'}>
                    <Box flex={2} paddingLeft={4}>
                        <Text style={{fontSize:20, color: '#666666'}} isTruncated>
                            {item.name}
                        </Text>
                    </Box>
                    <Box flex={1}>
                        <Text style={{ fontSize: 16, color : '#666666' }}>预算
                            <Text style={{fontSize : 16, color : '#ec655b'}}> {item.idealPrice}元</Text>
                        </Text>
                    </Box>
                </Flex>
                <Box px={4} pb={2}>
                    <Text
                        style={this.styles.descriptionText}
                        ellipsizeMode={'tail'}
                    >
                        {this.state.readMore ? item.description : null}
                        <Text style={{color:'#a0a0a0', fontSize:16}} onPress={()=> {
                            if(this.state.readMore === false) {
                                this.setState({readMore: true})
                            }
                            else{
                                this.setState({readMore: false})
                            }
                        }
                        }>{this.state.readMore?'收起':'展开详情'}</Text>
                    </Text>
                </Box>
            </VStack>
        )
    }
}
