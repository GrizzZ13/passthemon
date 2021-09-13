import * as React from 'react';
import {View, Text, ActivityIndicator} from 'react-native';
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";
import {listOnesDemandsByPage} from "../../service/DemandService";
import Spinner from "react-native-loading-spinner-overlay";
import {Box, FlatList} from "native-base";
import {DemandCard} from "../../components/DemandCard";
import BackHeader from "../../common/BackHeader";

export class MyDemand extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            demands: [],
            fetchPage: 0,
            refreshing: false,
            isEnd: false,
            loadingState: true,
        };
    }

    callback = data => {
        console.log("callback");
        if (data.list.length === 0) {
            console.log('no goods to be shown');
            this.setState({isEnd: true});
        } else {
            this.setState({isEnd: false});
        }
        this.setState({
            loadingState: false,
            refreshing: false,
            demands: data.list
        });
    };

    componentDidMount() {
            setTimeout(() => {
                this.setState({
                    loadingState: false,
                }); //停止刷新
            }, 3000);
            // console.log(ret.userid)
            this.setState({
                userId : this.props.route.params.userid,
            })
            let json = {
                fetchPage: this.state.fetchPage,
                userId: this.props.route.params.userid
            };
            listOnesDemandsByPage(json, this.callback);
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
            userId: this.state.userId
        };
        listOnesDemandsByPage(json, this.callback);
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

    render() {
        const {navigation} = this.props;
        if (this.state.userId===undefined || this.state.userId===null) return null;
        return (
            <NativeBaseProvider>
                <BackHeader text={'需求列表'}/>
                <FlatList
                    data={this.state.demands}
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
                                    console.log('data list')
                                    if (data.list.length === 0) {
                                        this.setState({
                                            isEnd: true,
                                        });
                                    } else {
                                        this.setState({
                                            fetchPage: this.state.fetchPage + 1,
                                            demands: this.state.demands.concat(data.list),
                                        });
                                    }
                                };

                                let json = {
                                    fetchPage: this.state.fetchPage + 1,
                                    userId: this.state.userId};
                                listOnesDemandsByPage(json, callback);
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
