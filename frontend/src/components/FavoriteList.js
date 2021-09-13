import React from "react";
import storage from "../config/Storage";
import {ActivityIndicator, Text, StyleSheet, TouchableHighlight, TouchableOpacity} from "react-native";
import Spinner from "react-native-loading-spinner-overlay";
import {Box, FlatList, HStack, Image, NativeBaseProvider,View, VStack} from "native-base";
import {deleteAllFavorite, listFavoriteByPage} from "../service/FavoriteService";
import CommonHead from "../common/CommonHead";
import UserIcon from "../pages/chat/UserIcon";
import {getAllImgForThisPage} from "../service/ImageService";


export class FavoriteList extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            favorite: [],
            imagesList: [],
            fetchPage: 0,
            refreshing: false,
            isEnd: false,
            loadingState: true,
        };
    }

    componentDidMount() {
        storage.load({key: 'loginState'}).then(ret => {
            setTimeout(() => {
                this.setState({
                    loadingState: false,
                }); //停止刷新
            }, 3000);
            console.log('ret.userid');
            this.setState({
                userId: ret.userid,
            });
            const callback = data => {
                if (data.list.length < 8) {
                    console.log('no more history to be shown');
                    this.setState({isEnd: true});
                } else {
                    this.setState({isEnd: false});
                }
                console.log('favorite:');
                this.setState({
                    favorite: data.list,
                    loadingState: false,
                    refreshing: false,
                });
                this.fetchImages(data.list);
            };
            let json = {userId: ret.userid, fetchPage: 0};
            listFavoriteByPage(json, callback);
        });
    }

    styles = StyleSheet.create({
        bookImage: {
            justifyContent: 'center',
            width: 125,
            height: 125,
        },
        bookNameText: {
            fontSize: 18,
            overflow: 'hidden',
        },
        priceText: {
            fontSize: 17,
            color: 'red',
        },
        favoriteTitle: {
            fontSize: 18,
            color : '#555555'
        },
        soldUsernameText: {
            fontSize: 14,
            paddingBottom: 6,
            paddingLeft: 2,
        },
        solderImage:{
            justifyContent: 'center',
            width: 30,
            height: 30,
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
    })

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
            console.log(data);
            this.setState({
                imagesList: this.state.imagesList.concat(data.list),
            })
        }
        getAllImgForThisPage(json, callback);
    }

    FavoriteCard({navigation}, item){
        for(let i = 0; i < this.state.imagesList.length; i++){
            if(item.id === this.state.imagesList[i].goodsId){
                item.image = this.state.imagesList[i].image;
                break;
            }
        }

        return(
            <TouchableHighlight
                onPress={() =>
                    navigation.navigate('HomeInside', {
                        goodsInfo: item,
                    })
                }>
                <HStack space={4}>
                    <Box px={4} pt={2} pb={2}>
                        {
                            item.image === null ?
                                <Image
                                    style={this.styles.bookImage}
                                    alt="goodsImage"
                                    source={require("../asset/img/preGoodsImage.png")}
                                />
                                :
                                <Image
                                    style={this.styles.bookImage}
                                    alt="goodsImage"
                                    source={{uri: 'data:image/jpg;base64,' + item.image}}
                                />
                        }
                    </Box>
                    <VStack space={2} width={160}>
                        <Box px={2} pt={4}>
                            <Text
                                style={this.styles.bookNameText}
                                numberOfLines={1}
                                ellipsizeMode={'tail'}>
                                {item.name}
                            </Text>
                        </Box>
                        <Box px={2}>
                            <Text style={this.styles.priceText}>{item.price}元</Text>
                        </Box>
                        <HStack>
                            <Box>
                                <UserIcon source={item.userImage} size={42}/>
                            </Box>
                            <Box alignSelf={'flex-end'}>
                                <Text style={this.styles.soldUsernameText}>{item.username}</Text>
                            </Box>
                        </HStack>
                    </VStack>
                </HStack>
            </TouchableHighlight>
        )
    }

    delete= () =>{
        const callback = data => {
            console.log(data);
            this.setState({
                favorite: [],
                isEnd: true,
            })
        };
        let json = {
            userId: this.state.userId,
        };
        console.log(this.state.userId);
        deleteAllFavorite(json, callback);
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
            <Text style={this.styles.favoriteTitle}>我的收藏</Text>
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
            <Text style={{fontSize: 16, textAlign: 'center', color: '#d0d0d0'}}>
                哎呀，到底啦！
            </Text>
        );

    render(){
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
                <FlatList
                    data={this.state.favorite}
                    mx="1%"
                    ListEmptyComponent={this.createEmptyView()}
                    ListFooterComponent={this.footerLoading()} //添加尾部组件
                    refreshing={this.state.refreshing}
                    onRefresh={this.onListRefresh}
                    onEndReachedThreshold={0.3}
                    onEndReached={({distanceFromEnd}) =>
                        setTimeout(() => {
                            if (this.state.isEnd === false) {
                                const callback = data => {
                                    if (data.list.length === 0) {
                                        this.setState({
                                            isEnd: true,
                                        });
                                    } else {
                                        this.setState({
                                            fetchPage: this.state.fetchPage + 1,
                                            favorite: this.state.favorite.concat(data.list),
                                        });
                                    }
                                    this.fetchImages(data.list);
                                };

                                let json = {
                                    fetchPage: this.state.fetchPage + 1,
                                    userId: this.state.userId
                                };
                                listFavoriteByPage(json, callback);
                            }
                        }, 0)
                    }
                    renderItem={({item}) => (
                        <View centerContent width="98%" mx="2%">
                            <Box borderRadius="md" my={1} backgroundColor="white">
                                {this.FavoriteCard({navigation}, item)}
                            </Box>
                        </View>
                    )}
                    keyExtractor={item => item.id}
                />
            </NativeBaseProvider>
        );
    }
}
