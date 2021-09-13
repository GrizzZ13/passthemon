import * as React from 'react';
import {View, Text, ActivityIndicator, Alert} from 'react-native';
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";
import {listAllDemandsByPage} from "../../service/DemandService";
import Spinner from "react-native-loading-spinner-overlay";
import {Box, CheckIcon, FlatList, HStack, Select} from "native-base";
import {DemandCard} from "../../components/DemandCard";

export class SquareDetail extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            demands: [],
            fetchPage: 0,
            refreshing: false,
            isEnd: false,
            loadingState: true,
            category: 0,
            attrition: 0,
        };
    }

    callback = data => {
        console.log(data);
        if (data.list.length === 0) {
            console.log('no goods to be shown');
            this.setState({isEnd: true});
        } else {
            this.setState({isEnd: false});
        }
        this.setState({
            loadingState: false,
            refreshing: false,
            demands: data.list,
        });
    };

    componentDidMount() {
        let json = {
            fetchPage: this.state.fetchPage,
            attrition: this.state.attrition,
            category: this.state.category
        };
        setTimeout(() => {
            this.setState({
                loadingState: false,
            }); //停止刷新
        }, 3000);
        listAllDemandsByPage(json, this.callback);
    }

    onListRefresh = () => {
        this.setState({
            refreshing: true,
            fetchPage: 0,
        }); //开始刷新
        //这里模拟请求网络，拿到数据，3s后停止刷新
        setTimeout(() => {
            this.setState({refreshing: false}); //停止刷新
        }, 3000);
        let json = {
            fetchPage: 0,
            attrition: this.state.attrition,
            category: this.state.category
        };
        listAllDemandsByPage(json, this.callback);
    };

    footerLoading = () =>
        this.state.isEnd === false ? (
            <ActivityIndicator size="large" color="#0000ff" />
        ) : (
            <Text style={{fontSize: 16, color: '#b0b0b0', alignSelf: 'center'}}>
                哎呀，到底啦！
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

    setCategory = itemValue =>{
        console.log(itemValue);
        console.log(parseInt(itemValue));
        this.setState({
                category: itemValue,
                fetchPage: 0,
            },
            ()=>{
                let json = {
                    fetchPage: this.state.fetchPage,
                    attrition: this.state.attrition,
                    category: this.state.category
                };
                listAllDemandsByPage(json, this.callback);
            })
    }

    setAttrition = itemValue =>{
        console.log(itemValue);
        console.log(parseInt(itemValue));
        this.setState({
                attrition: itemValue,
                fetchPage: 0,
            },
            ()=>{
                let json = {
                    fetchPage: this.state.fetchPage,
                    attrition: this.state.attrition,
                    category: this.state.category
                };
                listAllDemandsByPage(json, this.callback);
            })
    }

    Header = () =>{
        if(this.props.fromView !== "Mine") {
            return (
                <HStack>
                    <Select
                        variant="rounded"
                        width={'47%'}
                        ml={'1%'}
                        selectedValue={this.state.category}
                        accessibilityLabel="物品种类"
                        placeholder="物品种类"
                        onValueChange={itemValue => this.setCategory(itemValue)}
                        _selectedItem={{
                            bg: 'cyan.600',
                            endIcon: <CheckIcon size={4} />,
                        }}>
                        <Select.Item label="不限" value="0"/>
                        <Select.Item label="服饰装扮" value="1"/>
                        <Select.Item label="酒水零食" value="2"/>
                        <Select.Item label="电子产品" value="3"/>
                        <Select.Item label="游戏道具" value="4"/>
                        <Select.Item label="纸质资料" value="5"/>
                        <Select.Item label="生活用品" value="6"/>
                        <Select.Item label="其他" value="7"/>
                    </Select>
                    <Select
                        variant="rounded"
                        width={'47%'}
                        ml={'4%'}
                        mr={'1%'}
                        selectedValue={this.state.attrition}
                        accessibilityLabel="崭新程度"
                        placeholder="崭新程度"
                        onValueChange={itemValue => this.setAttrition(itemValue)}
                        _selectedItem={{
                            bg: 'cyan.600',
                            endIcon: <CheckIcon size={4}/>,
                        }}>
                        <Select.Item label="不限" value="0"/>
                        <Select.Item label="全新未用" value="-1"/>
                        <Select.Item label="几乎全新" value="-2"/>
                        <Select.Item label="轻微磨损" value="-3"/>
                        <Select.Item label="中度磨损" value="-4"/>
                        <Select.Item label="其他" value="-5"/>
                    </Select>
                </HStack>
            )
        }
    }


    render() {
        const {navigation} = this.props;
        return (
            <NativeBaseProvider>
                <FlatList
                    data={this.state.demands}
                    mx="1%"
                    ListEmptyComponent={this.createEmptyView()}
                    ListHeaderComponent={this.Header()}
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
                                        console.log(data);
                                        this.setState({
                                            fetchPage: this.state.fetchPage + 1,
                                            demands: this.state.demands.concat(data.list),
                                        });
                                    }
                                };

                                let json = {
                                    fetchPage: this.state.fetchPage + 1,
                                    attrition: this.state.attrition,
                                    category: this.state.category
                                };
                                listAllDemandsByPage(json, callback);
                            }
                        }, 0)
                    }
                    renderItem={({item}) => (
                        <View centerContent width="100%" px="1%">
                            <Box borderRadius="md" my={1} backgroundColor="white" shadow={3}>
                                <DemandCard item = {item} navigation ={navigation}/>
                            </Box>
                        </View>
                    )}
                    keyExtractor={item => item.id}
                />
            </NativeBaseProvider>
        );
    }
}
