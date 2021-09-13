import * as React from 'react';
import {
    // eslint-disable-next-line no-unused-vars
    Alert,
    View,
    Text,
    StyleSheet,
    TouchableHighlight
} from 'react-native';
import {
    Box,
    Center,
    Divider,
    FlatList,
    HStack,
    Button,
    Image, Pressable,
    ScrollView,
    VStack,
} from 'native-base';
import {DeviceEventEmitter} from "react-native";
import {NativeBaseProvider} from 'native-base/src/core/NativeBaseProvider';
import {getUserByUserId, getUserNameById} from '../../service/UserService';
import {findGoodsById,removeGoods} from '../../service/GoodsService';
import storage from '../../config/Storage';
import {addFavorite, checkFavorite, deleteFavorite} from "../../service/FavoriteService";
import Spinner from "react-native-loading-spinner-overlay";
import {addWants, checkWants, deleteWants} from "../../service/WantsService";
import {GoodsWantsList} from "../../common/GoodsWantsList";
import NavigationService from "../../service/NavigationService";
import UserIcon from "../chat/UserIcon";
import {getAllImgByGoodsId} from "../../service/ImageService";
import DialogInput from "react-native-dialog-input";
import {REMOVE_GOODS} from "../../config/UserConstant";
import {IMAGE_FAILED_AUDIT, ON_SALE, TEXT_FAILED_AUDIT} from "../../config/AuditConstant";

export class HomeDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            soleUser: [],
            soleUserId: -1,
            img: [],
            goodsId: 0,
            goodsInfo: [],
            loadingState: true,
            isWanting: false,
            disable:false,
            textInfo:'下架商品',
            imagesList: [],
            dialogVisible: false,
            userId:0,
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
                this.setState({
                    soleUser: data,
                });
            };
            let json = {userId: this.state.soleUserId};
            getUserByUserId(json, callback);

            const callback_2 = data => {
                this.setState({
                    goodsInfo: data,
                    loadingState: false,
                });
                if(data.state===0){
                    this.setState({
                        textInfo:'已下架',
                        disable:true,
                        loadingState: false,
                    })
                }
                else if(data.state===1){
                    this.setState({
                        textInfo:'下架商品',
                        loadingState: false,
                    })
                }
                else{
                    this.setState({
                        textInfo:'删除',
                        loadingState: false,
                    })
                }
            };
            let json_2 = {
                goodsId: this.state.goodsId,
                userId: ret.userid,
            };
            findGoodsById(json_2, callback_2);

            const callback_5 = data =>{
                this.setState({
                    imagesList: data.list,
                })
            }
            let json_5 = {
                goodsId: this.state.goodsId,
            }
            getAllImgByGoodsId(json_5, callback_5);

            if(this.state.userId !== this.state.soleUserId) {
                const callback_3 = (data) => {
                    if (data == null) {
                        this.setState({
                            isFavorite: false,
                        })
                    } else {
                        this.setState({
                            isFavorite: true,
                        })
                    }
                }
                let json_3 = {userId: ret.userid, goodsId: this.state.goodsId};
                checkFavorite(json_3, callback_3);

                const callback_4 = (data) => {
                    if (data == null) {
                        this.setState({
                            isWanting: false,
                        })
                    } else {
                        this.setState({
                            isWanting: true,
                        })
                    }
                }

                let json_4 = {userId: ret.userid, goodsId: this.state.goodsId};
                checkWants(json_4, callback_4);
            }
        });
    }
    componentWillUnmount() {
        if (this.listener) { this.listener.remove(); }
    }

    styles = StyleSheet.create({
        goodsImage: {
            justifyContent: 'center',
            marginLeft: 30,
            paddingRight: -25,
            width: 280,
            height: 280,
            marginBottom: 10,
            borderRadius: 10,
        },
        baseText: {
            fontSize: 18,
            color: '#666666',
        },
        userNameText: {
            fontSize: 20,
            marginLeft: 8,
            paddingRight: 12
        },
        creditText: {
            fontWeight: 'bold',
            fontSize: 20,
            color: '#ec655b',
        },
        priceText: {
            fontSize: 18,
            color: '#ec655b',
            paddingLeft: 10,
        },
        descriptionText: {
            fontSize: 18,
            paddingLeft: 8,
            paddingRight: 8,
            paddingBottom: 4,
        },
        bottomMenu: {
            height:"9%",
            position: 'absolute',
            backgroundColor: "white"
        },
        inventoryText: {
            fontSize: 13,
            color: '#ec655b',
        },
    });
    renderBottom=()=>{
        switch (this.state.goodsInfo.state) {
            case ON_SALE:
                return( <GoodsWantsList
                    goodsId = {this.state.goodsId}
                />);
            case IMAGE_FAILED_AUDIT:
                return (
                    <Text style={{color:'red',fontSize: 20,fontWeight: 'bold'}}>商品图片审核不通过</Text>
                );
            case TEXT_FAILED_AUDIT:
                return (
                    <Text style={{color:'red',fontSize: 20,fontWeight: 'bold'}}>商品名或描述审核不通过</Text>
                );
        }
    }
    removeMyGoods=()=>{
        this.setState(
            {disable:true}
        );
        const callback=(data)=>{
            let param={
                type:REMOVE_GOODS,
            }
            DeviceEventEmitter.emit('EditUser', param);
            this.setState({
                disable:true,
                textInfo:'已下架'
            });
        };
        const param={
            goodsId:this.state.goodsId,
            userId:this.state.userId,
        }
        removeGoods(param,callback);
    }

    renderRightComp=()=>{
        if(this.state.userId!==this.state.soleUserId){
            return(
                <Box width={'50%'}>
                    <Text style={this.styles.baseText}>
                        信誉分数：
                        <Text style={this.styles.creditText}>
                            {this.state.soleUser.credit}
                        </Text>
                    </Text>
                </Box>);
        }
        else if(this.state.goodsInfo.state==1) {
            return(
                <HStack width={'50%'}>
                    <Pressable onPress={()=>{this.props.navigation.navigate('EditGood',{goodId:this.state.goodsId})}} mr={'5'}>
                        <Center>
                            <Text style={this.styles.baseText}>
                                编辑
                            </Text>
                        </Center>
                    </Pressable>
                    <Pressable onPress={this.showConfirm}>
                        <Center>
                            <Text style={this.styles.baseText}>
                                {this.state.textInfo}
                            </Text>
                        </Center>
                    </Pressable>
                </HStack>);
        }else {
            return(
                <HStack width={'50%'}>
                    <Pressable onPress={()=>{this.props.navigation.navigate('EditGood',{goodId:this.state.goodsId})}} mr={'5'}>
                        <Center>
                            <Text style={this.styles.baseText}>
                                修整
                            </Text>
                        </Center>
                    </Pressable>
                    <Pressable onPress={this.showConfirm}>
                        <Center>
                            <Text style={this.styles.baseText}>
                                {this.state.textInfo}
                            </Text>
                        </Center>
                    </Pressable>
                </HStack>);
        }
    }

    isPositiveInteger = (text) => {
        let reg = /^[+]{0,1}(\d+)$/;
        return reg.test(text);
    }

    submitInput = (inputText) => {
        if(!this.isPositiveInteger(inputText))
            Alert.alert('提示','请输入有效数量！',[{text:'知道了',style:'cancel'}],{cancelable:false});
        else{
            let number = Number(inputText);
            if (number > this.state.goodsInfo.inventory) {
                Alert.alert('提示', '余货不足！',
                    [{text: '知道了'}]
                    , {cancelable: false});
            }
            else{
                const callback = (data) => {
                    this.setState({
                        isWanting: true,
                        dialogVisible: false,
                    })
                }
                let json = {
                    userId: this.state.userId,
                    sellerId: this.state.soleUserId,
                    goodsId: this.state.goodsId,
                    num: number,
                }
                addWants(json, callback);
            }
        }
    }

    onWantPressed = () => {
        if(this.state.isWanting === false) {
            this.setState({dialogVisible: true});
        }
        else{
            Alert.alert('提示','真的不想要了吗？',[
                {text:'确认',onPress:() => {
                        const callback = data => {
                            this.setState({
                                isWanting: false
                            })
                        }
                        let json = {
                            userId: this.state.userId,
                            sellerId: this.state.soleUserId,
                            goodsId: this.state.goodsId,
                        }
                        deleteWants(json, callback);} },
                {text:'取消',style:'cancel'}
            ],{cancelable:false});
        }
    }

    showConfirm=()=>{
        if(!this.state.disable){
            Alert.alert('提示','确认下架商品？',[
                {text:'确认',onPress:() => {this.removeMyGoods()} },
                {text:'取消',style:'cancel'}
            ],{cancelable:false});
        }};
    render() {
        const {navigation, route} = this.props;
        const {goodsInfo} = route.params;
        this.state.goodsId = goodsInfo.id;
        this.state.soleUserId = goodsInfo.userId;
        let favoriteFontSize = 14;
        let wantFontSize = 14;
        return (
            <NativeBaseProvider>
                <Spinner
                    visible={this.state.loadingState}
                    textContent={'Loading...'}
                    textStyle={{
                        color: '#FFF',
                    }}
                />
                <DialogInput isDialogVisible={this.state.dialogVisible}
                                            title={"确认请求"}
                                            message={'请输入数量'}
                                            hintInput ={"不少于一个"}
                                            submitInput={this.submitInput}
                                            closeDialog={ () => {
                                                this.setState({dialogVisible:false});
                                            }}>
                </DialogInput>
                <ScrollView>
                    <Box width="96%" ml="2%" mb={"16%"}>
                        <VStack divider={<Divider />}>
                            <Box>
                                <VStack space={4} divider={<Divider />}>
                                    <Box px={4} mt={5}>
                                        <HStack space={10} alignItems="center">
                                            <Box width={'50%'}>
                                                <HStack alignItems="center">
                                                    <TouchableHighlight
                                                        onPress={() => {
                                                            if (this.state.soleUserId !== this.state.userId) {
                                                                navigation.navigate('Other', {
                                                                    userid: this.state.soleUserId,
                                                                })
                                                            }
                                                            else{
                                                                navigation.navigate('Mine')
                                                            }
                                                        }
                                                        }>
                                                        <UserIcon source={this.state.soleUser.image} size={50}/>

                                                    </TouchableHighlight>
                                                    <Text style={this.styles.userNameText}>
                                                        {this.state.soleUser.username}
                                                    </Text>
                                                </HStack>
                                            </Box>
                                            {this.renderRightComp()}
                                        </HStack>
                                    </Box>
                                    <Box>
                                        <HStack px={4} space={10} alignItems="center">
                                            <Box width={'50%'}>
                                                <Text style={this.styles.baseText}>
                                                    {this.state.goodsInfo.name}
                                                    <Text style={this.styles.inventoryText}>
                                                        (剩余 {this.state.goodsInfo.inventory} 件)
                                                    </Text>
                                                </Text>
                                            </Box>
                                            <Box width={'50%'}>
                                                <Text style={this.styles.priceText}>
                                                    {this.state.goodsInfo.price}元/件
                                                </Text>
                                            </Box>
                                        </HStack>
                                    </Box>
                                    <Box>
                                        {
                                            this.state.imagesList.length === 0?
                                                <View>
                                                    <Image
                                                        style={this.styles.goodsImage}
                                                        alt="goodsImage"
                                                        source={(require("../../asset/img/preGoodsImage.png"))}
                                                    />
                                                </View>
                                                :
                                                <FlatList
                                                    horizontal={true}
                                                    showsHorizontalScrollIndicator={true}
                                                    data={this.state.imagesList}
                                                    renderItem={({item}) => (
                                                        <View style={this.styles.container}>
                                                            <Image
                                                                style={this.styles.goodsImage}
                                                                alt="goodsImage"
                                                                source={{uri: 'data:image/jpg;base64,' + item}}
                                                            />
                                                        </View>
                                                    )}
                                                    keyExtractor={item => item.displayOrder}
                                                />
                                        }
                                    </Box>
                                    <Box>
                                        <Text style={this.styles.descriptionText}>
                                            {this.state.goodsInfo.description}
                                        </Text>
                                    </Box>
                                </VStack>
                            </Box>
                        </VStack>
                    </Box>
                </ScrollView>
                {this.state.userId !== this.state.soleUserId?
                    <View style={this.styles.bottomMenu} width={'100%'} bottom={0}>
                        <Center paddingTop={'3%'}>
                            <HStack space={4} alignItems="center">
                                <TouchableHighlight
                                    onPress={()=> {
                                        const callback=data=>{
                                            let json = {
                                                authorUsername: data.username,
                                                authorId: this.state.userId,
                                                recipientUsername: this.state.soleUser.username,
                                                recipientId: this.state.soleUser.id,
                                                channelId: null,
                                            }
                                            NavigationService.navigate('ChatInside', {channelInfo: json});
                                        }
                                        getUserNameById({userId: this.state.userId}, callback);
                                    }}
                                >
                                    <View>
                                        <Image alt={'Chat'} source={require('../../asset/img/Msg.png')} width={12} height={8} />
                                    </View>
                                </TouchableHighlight>
                                <Button
                                    colorScheme={"orange"}
                                    _text={{color: '#ffffff',
                                        fontSize: favoriteFontSize
                                    }}
                                    width={32}
                                    borderRadius={20}
                                    onPress={() => {
                                        let json = {};
                                        json.userId = this.state.userId;
                                        json.goodsId = this.state.goodsId;
                                        if(this.state.isFavorite === false) {
                                            const callback = data => {
                                                this.setState({
                                                    isFavorite: true,
                                                });
                                            };
                                            addFavorite(json, callback);
                                        }
                                        else{
                                            const callback = data => {
                                                this.setState({
                                                    isFavorite: false,
                                                });

                                            };
                                            deleteFavorite(json, callback);
                                        }
                                    }}
                                >
                                    {this.state.isFavorite === false ? "收藏" : "取消收藏"}
                                </Button>
                                <Button
                                    width={32}
                                    colorScheme={'red'}
                                    borderRadius={20}
                                    _text={{color: '#ffffff',
                                        fontSize: wantFontSize}}
                                    onPress={this.onWantPressed}
                                >{this.state.isWanting ? "取消想要" : "想要"}
                                </Button>
                            </HStack>
                        </Center>
                    </View>
                    :
                    <Box height={200}>
                        {this.renderBottom()}
                    </Box>
                }
            </NativeBaseProvider>
        );
    }
}
