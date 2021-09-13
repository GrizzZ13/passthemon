import React from "react";
import {
    NativeBaseProvider,
    Pressable,
    Text,
    TextArea,
    VStack,
    FormControl,
    Radio,
    Button,
    ScrollView, Collapse, Alert, IconButton, CloseIcon
} from "native-base";
import CommonHead from "../../common/CommonHead";
import Icon from "react-native-vector-icons/FontAwesome";
import Box from "native-base/src/components/primitives/Box/index";
import {commentOnOrderByUserId} from "../../service/OrderService";
import storage from "../../config/Storage";

export class Comment extends React.Component{
    constructor() {
        super();
    }
    state={
        title:'',
        comment:'',
        rating:'',
        orderId:0,
        opt:0,  // 1代表买家提交评价，2代表买家查看修改评价，3代表仅能查看评价
        goodsName:'',
        disabled:false,
        show: false, //是否展示提示条
        showInfo:'评价成功',
        userid:0,
    }
    componentDidMount() {
        storage
            .load({key: 'loginState'})
            .then(ret => {
                this.setState({userid: ret.userid});
            })
        this.setState({
            opt:this.props.route.params.opt,
            orderId:this.props.route.params.orderId,
            goodsName:this.props.route.params.goodsName,
            comment:this.props.route.params.comment,
            rating:this.props.route.params.rating.toString(),
        });
     switch (this.props.route.params.opt) {
         case 1:
             this.setState({title:'提交评价'});
             break;
         case 2:
             this.setState({title:'查看我的评价'});
             break;
         case 3:
             this.setState({title:'查看买家评价'});
             break;

     }
    }
    renderLeft() {
        return (
            <Pressable ml={'2'} onPress={() => this.props.navigation.goBack()}>
                <Icon size={25} name="arrow-left" backgroundColor="#3b5998" />
            </Pressable>
            // <Button onPress={() => this.props.navigation.goBack()} title="Dismiss" />
        );
    }
    renderTitle() {
        return (
            <Text mr={'10'} fontFamily={'SimSun'} fontSize={'xl'}>
                {this.state.title}
            </Text>
        );
    }
    inputComment = (value)=>{
        this.setState({comment:value});
    }
    uploadComment = ()=>{
        this.setState({
            disabled:true,
        });
        let params={
            userId: this.state.userid,
            orderId:this.state.orderId,
            comment:this.state.comment,
            rating:this.state.rating,
        }
        const callback=(data)=>{
            this.setState({
                disabled:false,
                showInfo:'评价提交成功',
                show:true,
            })
        }
        commentOnOrderByUserId(params,callback);
    }
    editMyComment = ()=>{
        this.setState({
            disabled:true,
        });
        let params={
            userId:this.state.userid,
            orderId:this.state.orderId,
            comment:this.state.comment,
            rating:this.state.rating,
        }
        const callback=(data)=>{
            this.setState({
                disabled:false,
                showInfo:'评价修改成功',
                show:true,
            })
        }
        commentOnOrderByUserId(params,callback);
    }
    renderComment=()=>{
        switch (this.state.opt) {
            case 1:
                return (
                    <VStack space={4}>
                        <Text>输入评价</Text>
                        <TextArea
                            style={{
                                borderColor: '#dddddd',
                                borderWidth: 0.9,
                            }}
                            width={'100%'}
                            value={this.state.comment}
                            onChangeText={text => {
                                this.inputComment(text);
                            }}
                            aria-label="t1"
                            numberOfLines={4}
                            placeholder="输入评价"
                            isInvalid
                            _dark={{
                                placeholderTextColor: 'gray.300',
                            }}
                        />
                        <FormControl isRequired isInvalid>
                            <FormControl.Label>为此次交易打分</FormControl.Label>
                            <Radio.Group
                                defaultValue="1"
                                name="exampleGroup"
                                value={this.state.rating}
                                onChange={(nextValue) => {
                                    this.setState({rating:nextValue})
                                }}>
                                <Radio value="1" my={1}>
                                    1
                                </Radio>
                                <Radio value="2" my={1}>
                                    2
                                </Radio>
                                <Radio value="3" my={1}>
                                    3
                                </Radio>
                                <Radio value="4" my={1}>
                                    4
                                </Radio>
                                <Radio value="5" my={1}>
                                    5
                                </Radio>
                            </Radio.Group>
                        </FormControl>
                    </VStack>
                );
            case 2:
                return (
                    <VStack space={4}>
                        <Text>我的评价</Text>
                        <TextArea
                            style={{
                                borderColor: '#dddddd',
                                borderWidth: 0.9,
                            }}
                            width={'100%'}
                            value={this.state.comment}
                            onChangeText={text => {
                                this.inputComment(text);
                            }}
                            aria-label="t1"
                            numberOfLines={4}
                            placeholder="输入评价"
                            isInvalid
                            _dark={{
                                placeholderTextColor: 'gray.300',
                            }}
                        />
                        <FormControl isRequired isInvalid>
                            <FormControl.Label>我的评分</FormControl.Label>
                            <Radio.Group
                                defaultValue="1"
                                name="exampleGroup"
                                value={this.state.rating}
                                onChange={(nextValue) => {
                                    this.setState({rating:nextValue})
                                }}>
                                <Radio value="1" my={1}>
                                    1
                                </Radio>
                                <Radio value="2" my={1}>
                                    2
                                </Radio>
                                <Radio value="3" my={1}>
                                    3
                                </Radio>
                                <Radio value="4" my={1}>
                                    4
                                </Radio>
                                <Radio value="5" my={1}>
                                    5
                                </Radio>
                            </Radio.Group>
                        </FormControl>
                    </VStack>
                );
            case 3:
                return (
                    <Box>
                        <Text>买家的评价</Text>
                        <TextArea
                            style={{
                                borderColor: '#dddddd',
                                borderWidth: 0.9,
                            }}
                            width={'100%'}
                            value={this.state.comment}
                            aria-label="t1"
                            numberOfLines={4}
                            placeholder="输入评价"
                            isDisabled
                            _dark={{
                                placeholderTextColor: 'gray.300',
                            }}
                        />
                        <Text>{'买家的评分:'+this.state.rating}</Text>
                    </Box>
                );
        }
    }
    renderButton=()=>{
        switch (this.state.opt) {
            case 1:
                return(
                    <Button disabled={this.state.disabled}
                            onPress={this.uploadComment}>
                        提交评价
                    </Button>
                )
            case 2:
                return (
                    <Button disabled={this.state.disabled}
                            onPress={this.editMyComment}>
                        修改评价
                    </Button>
                )
            case 3:
                return (
                    <Button onPress={()=>{this.props.navigation.goBack()}}>
                        返回上页
                    </Button>
                )
        }
    }
    render(){
        return(
            <NativeBaseProvider>
                <ScrollView>
                    <Collapse isOpen={this.state.show}>
                        <Alert
                            status="success"
                            action={
                                <IconButton
                                    icon={<CloseIcon size="xs" />}
                                    onPress={() => {
                                        this.setState({show: false});
                                    }}
                                />
                            }
                            actionProps={{
                                alignSelf: 'center',
                            }}>
                            <Alert.Icon />
                            <Alert.Title flexShrink={1}>{this.state.showInfo}</Alert.Title>
                        </Alert>
                    </Collapse>
                <CommonHead
                    leftItem={() => this.renderLeft()}
                    titleItem={() => this.renderTitle()}
                    navBarColor={'#f2f2f2'}
                />
                <VStack paddingTop={5} space={5} width={'90%'} marginLeft={'5%'}>
                    <Text>{'商品名称:   '+this.state.goodsName}</Text>
                    {this.renderComment()}
                    {this.renderButton()}
                </VStack>
                </ScrollView>
            </NativeBaseProvider>
        );
    }
}
