import * as React from "react";
import {Text, Button, TouchableOpacity, DeviceEventEmitter, Alert} from "react-native";
import {Box, Center, FlatList, Flex, VStack} from "native-base";
import {getWanter} from "../service/GoodsService";
import NavigationService from "../service/NavigationService";
import storage from "../config/Storage";
import {getUserByUserId} from "../service/UserService";
import {affirmWants} from "../service/OrderService";
import UserIcon from "../pages/chat/UserIcon";
import {deleteWants} from "../service/WantsService";

export class GoodsWantsList extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            wanter: [],
            soleUserId: 0,
        };
    }

    componentDidMount() {
        console.log(this.props.goodsId)
        const callback = data => {
            console.log(data)
            this.setState({
                wanter: data.list,
            });
        };
        let json = {
            goodsId: this.props.goodsId,
        };
        getWanter(json, callback);
        storage.load({key: 'loginState'}).then(ret => {
            const callback = data => {
                console.log(data);
                this.setState({
                    soleUser: data
                });
            };
            let json = {userId: ret.userid};
            getUserByUserId(json, callback);
        })
    }

    render() {
        return(
            <Box>
                <Box h={1} width={'100%'} backgroundColor={'#dddddd'}>
                </Box>
                <Box width="96%" ml="2" mb={"2"}>
                    <FlatList
                        data={this.state.wanter}
                        ListHeaderComponent={
                            <Box height={10}>
                                <Text
                                    style={{color: '#555555', textAlign: "center", fontSize: 16}}
                                >想要的人</Text>
                            </Box>
                        }
                        renderItem={({item}) => (
                            <TouchableOpacity
                                onPress={()=> {
                                    let json = {
                                        authorUsername: this.state.soleUser.username,
                                        authorId: this.state.soleUser.id,
                                        recipientUsername: item.wanterName,
                                        recipientId: item.wanterId,
                                        channelId: null,
                                    }
                                    console.log(json);
                                    NavigationService.navigate('ChatInside', {channelInfo: json});
                                }}
                            >
                                <Box height={16}>
                                    <Flex direction={'row'}>
                                        <Box flex={2}>
                                            <TouchableOpacity
                                                onPress={()=> {
                                                    NavigationService.navigate('Other', {
                                                        userid: item.wanterId,
                                                    })}}
                                            >
                                                <UserIcon size={47} source={item.wanterImage}/>
                                            </TouchableOpacity>
                                        </Box>
                                        <VStack flex={4}>
                                            <Text style={{fontSize: 16}}>{item.wanterName}</Text>
                                            <Text style={{fontSize: 12, color: '#a0a0a0'}}>{item.time}</Text>
                                        </VStack>
                                        <Center flex={2}>
                                            <Box>
                                                <Text style={{fontSize: 16}}>数量：{item.num}</Text>
                                            </Box>
                                        </Center>
                                        <Center flex={2}>
                                            <Box>
                                                <Button
                                                    title={"交易"}
                                                    color={'#ec655b'}
                                                    onPress={()=>{
                                                        Alert.alert('提示','确认进行交易？',[
                                                            {text:'确认',onPress:() => {
                                                                    let param={
                                                                        type:4,
                                                                    }
                                                                    DeviceEventEmitter.emit('EditUser', param);
                                                                    Alert.alert('提示','您已完成交易！',[
                                                                        {text:'确认',style:'cancel'}
                                                                    ],{cancelable:false})
                                                                    const callback=data=>{
                                                                        console.log(data);
                                                                        this.setState({
                                                                            wanter: []
                                                                        })
                                                                    }
                                                                    let json = {
                                                                        wantsId: item.id,
                                                                        userId: this.state.soleUser.id
                                                                    }
                                                                    console.log(item.id);
                                                                    affirmWants(json, callback);} },
                                                            {text:'取消',style:'cancel'}
                                                        ],{cancelable:false});
                                                    }}
                                                />
                                            </Box>
                                        </Center>
                                    </Flex>
                                </Box>
                            </TouchableOpacity>
                        )}
                        keyExtractor={item => item.wanterId}
                    />
                </Box>
            </Box>
        )
    }
}
