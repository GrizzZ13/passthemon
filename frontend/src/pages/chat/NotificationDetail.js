import React from "react";
import storage from "../../config/Storage";
import {
    Center, HStack,
    Input,
    NativeBaseProvider,
    ScrollView,
    Text, TextArea,
    VStack
} from "native-base";
import Box from "native-base/src/components/primitives/Box/index";
import BackHeader from "../../common/BackHeader";

export class NotificationDetail extends React.Component{
    constructor() {
        super();
    }
    state={
        title:'',
        content:'',
        date:'',
    }
    componentDidMount() {
        storage
            .load({key: 'loginState'})
            .then(ret => {
                this.setState({userid: ret.userid});
            })
        this.setState({
            title:this.props.route.params.title,
            content:this.props.route.params.content,
            date:this.props.route.params.date,
        });
    }

    render(){
        return(
            <NativeBaseProvider>
                <ScrollView>
                    <BackHeader text={'通知详情'}/>
                    <VStack paddingTop={4} space={4} width={'90%'} marginLeft={'5%'}>
                        <Center>
                            <Text
                                style={{
                                    fontSize:20,
                                    color:'#505050'
                                }}>
                                {this.state.title}
                            </Text>
                        </Center>
                        <Center>
                            <Text
                                style={{
                                    fontSize:14,
                                    color:'#808080'
                                }}>
                                {this.state.date}
                            </Text>
                        </Center>
                        <Text
                            style={{
                                fontSize:17,
                                color:'#606060'
                            }}
                        >
                            {this.state.content}
                        </Text>
                    </VStack>
                </ScrollView>
            </NativeBaseProvider>
        );
    }
}
