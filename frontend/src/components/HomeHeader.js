import React from "react";
import CommonHead from "../common/CommonHead";
import {DeviceEventEmitter, StyleSheet, TouchableOpacity} from "react-native";
import { HStack, Image, Input} from "native-base";
import {searchGoods} from "../service/GoodsService";
import NavigationService from '../service/NavigationService';

export class HomeHeader extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            searchText: null,
        };
    }

    startEmit = (data) => {
        DeviceEventEmitter.emit('changeResult', data);
    };

    goodsSearch(text) {
        console.log(text);
        this.setState({
            searchText: text,
        })

    }

    doSearch() {
        let json = {search: this.state.searchText, fetchPage: 0}
        const callback = data => {
            console.log(data);
            let listenPast = {
                searchText: this.state.searchText,
                result: data
            }
            this.startEmit(listenPast);
            NavigationService.navigate('HomeSearch', this.props.navigation)
        }
        searchGoods(json, callback);
    }

    renderTitleItem() {
        return (
            <HStack style={{ width: '100%', height: '100%'}} alignItems="center">
                <Image alt={"icon"} source={require('../asset/img/icon.jpg')}
                       style={{width: 40, height: 40, marginRight: 10}}/>
                <Input
                    variant={'rounded'}
                    paddingTop={'0'}
                    paddingBottom={'0'}
                    w={'72%'}
                    h={'80%'}
                    placeholder="search"
                    _light={{
                        placeholderTextColor: 'blueGray.400',
                    }}
                    _dark={{
                        placeholderTextColor: 'blueGray.50',
                    }}
                    onFocus={() => {
                        NavigationService.navigate("HomeSearch");
                    }}
                    onChangeText={text => {
                        this.goodsSearch(text);
                    }}
                />
                <TouchableOpacity onPress={() => this.doSearch()}>
                    <Image
                        style={{width: 25, height: 25, marginLeft: 10, marginTop: 5}}
                        source={require('../asset/img/search.png')}
                        alt={'搜索'}
                    />
                </TouchableOpacity>
            </HStack>
        );
    }

    render() {
        return (
            <CommonHead
                titleItem={() => this.renderTitleItem()}
            />
        )
    }
}
