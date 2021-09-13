//此页面专用于跳转
import React from "react";
import {PERMISSION_DENIED, REQUEST_EXCESSIVE, VISITING_TOO_OFTEN} from "../../config/Message";
import {Alert, Center, HStack, NativeBaseProvider, Button, ScrollView} from "native-base";
import NavigationService from "../../service/NavigationService";
import Image from "native-base/src/components/primitives/Image/index";
export class Error extends React.Component{
    constructor() {
        super();
    }
    state={
        text:'',
    }
    componentDidMount() {
        if(this.props.route.params.type==PERMISSION_DENIED){
            this.setState({
                text:'抱歉，您上次操作的权限不足',
            })
        }
        else if(this.props.route.params.type==VISITING_TOO_OFTEN){
            this.setState({
                text:'出错啦，您的操作过于频繁',
            })
        }
        else if(this.props.route.params.type==REQUEST_EXCESSIVE){
            this.setState({
                text:'服务器访问繁忙，请您稍后再试',
            })
        }
    }
    render(){
        return(
            <NativeBaseProvider>
                <ScrollView>
                <Center>
                    <Alert variant="top-accent" status="error">
                        <Alert.Icon />
                        <Alert.Title flexShrink={1}>{this.state.text}</Alert.Title>
                    </Alert>
                    <Image source={require('./error.jpg')} alt={' '}/>
                <HStack mt={'300'} space={3}>
                    <Button onPress={()=>{this.props.navigation.goBack()}}>
                        返回上页
                    </Button>
                    <Button onPress={()=>{NavigationService.navigate('Main')}}>
                        回到主页
                    </Button>
                </HStack>
                </Center>
                </ScrollView>
            </NativeBaseProvider>
        );
    }
}
