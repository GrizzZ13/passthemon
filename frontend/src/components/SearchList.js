import React from 'react';
import {
    Box,
    FlatList,
    Center,
    NativeBaseProvider,
    Stack,
    Image,
    Heading,
    VStack,
    Divider,
    HStack,
    Button,
    View,
} from 'native-base';
import {searchGoods} from '../service/GoodsService';
import {
    StyleSheet,
    TouchableHighlight,
    Text,
    ActivityIndicator, DeviceEventEmitter, Alert,
} from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import NavigationService from "../service/NavigationService";
import {getAllImgForThisPage} from "../service/ImageService";

export class SearchList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            goodsData: [],
            fetchPage: 0,
            isEnd: true,
            loadingState: false,
            userId: 0,
            isTimeOut: false
        };
    }

    callback = data => {
        console.log(data.list);
        if (data.list.length === 0) {
            console.log('no goods to be shown');
            this.setState({isEnd: true});
        } else {
            this.setState({isEnd: false});
        }
        this.setState({
            loadingState: false,
            refreshing: false,
            goodsData: data.list,
            imagesList: [],
        });
    };

    componentDidMount() {
        this.listener = DeviceEventEmitter.addListener('changeResult', (searchData) => {
            // 收到监听后想做的事情 // 监听
            let data = searchData.result;

            if (data.list.length === 0) {
                console.log('no goods to be shown');
                Alert.alert('提示','没有此种商品',[
                    {text:'确认',style:'cancel'}]
                );
                this.setState({isEnd: true});
            } else {
                this.setState({isEnd: false});
            }
            this.setState({
                    searchText: searchData.searchText,
                    goodsData: data.list,
                    imagesList: []
                },
                this.fetchImages(data.list)
            );
        });
    }

    fetchImages = (goodsList) =>{
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
        getAllImgForThisPage(json, callback)
    }

    styles = StyleSheet.create({
        priceText: {
            fontSize: 17,
            color: 'red',
        },
        goodsNameText: {
            fontSize: 17,
            fontWeight: 'bold',
            overflow: 'hidden',
        },
        loadingContainer: {
            flex: 1,
            justifyContent: 'center',
        },
    });

    GoodsCard({navigation}, item) {
        for(let i = 0; i < this.state.imagesList.length; i++){
            if(item.id === this.state.imagesList[i].goodsId){
                item.image = this.state.imagesList[i].image;
                break;
            }
        }
        return (
            <TouchableHighlight
                onPress={() =>
                    navigation.navigate('HomeInside', {
                        goodsInfo: item,
                    })
                }>
                <VStack space={2} divider={<Divider />}>
                    <Box px={4} pt={4}>
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
                    <Box px={4}>
                        <Text
                            style={this.styles.goodsNameText}
                            numberOfLines={1}
                            ellipsizeMode={'tail'}>
                            {item.name}
                        </Text>
                    </Box>
                    <Box>
                        <HStack px={4} pb={4} space={3} alignItems="center">
                            <Text style={this.styles.priceText}>{item.price}元</Text>
                        </HStack>
                    </Box>
                </VStack>
            </TouchableHighlight>
        );
    }

    footerLoading = () =>
        this.state.isEnd === false ? (
            <ActivityIndicator size="large" color="#0000ff" />
        ) : (
            <Text>
            </Text>
        );

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
        const {navigation} = this.props;
        return (
            <NativeBaseProvider>
                <FlatList
                    data={this.state.goodsData}
                    numColumns={2}
                    mx="1%"
                    ListEmptyComponent={this.createEmptyView()}
                    ListFooterComponent={this.footerLoading()} //添加尾部组件】
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
                                        console.log(data);
                                        this.setState({
                                            fetchPage: this.state.fetchPage + 1,
                                            goodsData: this.state.goodsData.concat(data.list),
                                        });
                                    }
                                    this.fetchImages(data.list);
                                };

                                let json = {
                                    fetchPage: this.state.fetchPage + 1,
                                    search: this.state.searchText
                                };
                                searchGoods(json, callback);
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
