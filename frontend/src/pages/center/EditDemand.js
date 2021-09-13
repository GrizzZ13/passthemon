import React from 'react';
import {
  NativeBaseProvider,
  ScrollView,
  TextArea,
  Button,
  Input,
  Stack,
  VStack,
  Select,
  CheckIcon,
  Pressable,
  Alert,
  IconButton,
  CloseIcon,
  Collapse,
  Text,
} from 'native-base';
import {CheckPrice, CheckPositive} from '../../utils/TextUtil';
import {getDemandById, editDemand} from '../../service/DemandService';
import CommonHead from '../../common/CommonHead';
import Spinner from 'react-native-loading-spinner-overlay';
import Icon from 'react-native-vector-icons/FontAwesome';
import {getTag} from "../../utils/GoodUtil";
import {getTypeConstant,getWearConstant} from "../../utils/GoodUtil";
import storage from "../../config/Storage";
export class EditDemand extends React.Component {
  constructor() {
    super();
  }
  state = {
    description: '', //商品描述
    num: '',
    name: '',
    idealPrice: '',
    demandId: 0,
    label1: '',
    label2: '',
    loadingState: true,
    show: false,
    disable: false,
    buttonText: '提交修改',
    warn:false,
    warnText:'',
    userid:0
  };
  setLabel1 = value => {
    this.setState({
      label1: value,
    });
  };
  setLabel2 = value => {
    this.setState({label2: value});
  };
  //输入描述
  inputDescription = value => {
    this.setState({description: value});
  };
  componentDidMount() {
    this.setState({demandId: this.props.route.params.demandId});
    const callback = data => {
      this.setState({
        description: data.description,
        num: data.num.toString(),
        name: data.name,
        idealPrice: data.idealPrice.toString(),
        label1: getTag(data.category),
        label2: getTag(data.attrition),
        loadingState: false,
      });
    };
    storage.load({key: 'loginState'})
        .then(ret => {
          this.setState({userid: ret.userid});
        });
       let param = {
         demandId: this.props.route.params.demandId,
       };
      setTimeout(() => {
      this.setState({
        loadingState: false,
      });
    }, 3000);
      getDemandById(param, callback);
  }
  //头部中间组件
  renderTitle() {
    return (
      <Text mr={'10'} fontFamily={'SimSun'} fontSize={'xl'}>
        编辑需求物品
      </Text>
    );
  }
  renderLeft() {
    return (
      <Pressable ml={'2'} onPress={() => this.props.navigation.goBack()}>
        <Icon size={25} name="arrow-left" backgroundColor="#3b5998" />
      </Pressable>
      // <Button onPress={() => this.props.navigation.goBack()} title="Dismiss" />
    );
  }
  //输入数量
  inputNum = value => {
    this.setState({num: value});
  };
  //输入价格
  inputPrice = value => {
    this.setState({idealPrice: value});
  };
  //输入名称
  inputName = value => {
    this.setState({name: value});
  };
  //编辑修改需求物品
  edit = () => {
    this.setState({
      disable: true,
      buttonText: '正在上传修改',
    });
    let labels = [];
    if (this.state.label1 !== '') {
      labels.push(getTypeConstant(this.state.label1));
    }
    if (this.state.label2 !== '') {
      labels.push(getWearConstant(this.state.label2));
    }
    let data = {
      demandId: this.state.demandId,
      name: this.state.name,
      num: this.state.num,
      description: this.state.description,
      idealPrice: this.state.idealPrice,
      labels: labels,
      userId:this.state.userid,
    };
    const callback = data => {
      if(data!==undefined){
      this.setState({
        show: true,
        buttonText: '提交修改',
        disable: false,
      });}
      else {
        this.setState({
          warn:true,
          warnText:'权限不足，无法修改',
          disable:true,
          buttonText:'权限不足，无法修改',
        })
      }
    };
    editDemand(data, callback);
  };
  render() {
    return (
      <NativeBaseProvider>
        <Spinner
          visible={this.state.loadingState}
          textContent={'Loading...'}
          textStyle={{
            color: '#FFF',
          }}
        />
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
              <Alert.Title flexShrink={1}>修改成功，请等待审核结果</Alert.Title>
            </Alert>
          </Collapse>
          <Collapse isOpen={this.state.warn}>
            <Alert
                status="error"
                action={
                  <IconButton
                      icon={<CloseIcon size="xs" />}
                      onPress={() => {
                        this.setState({warn: false});
                      }}
                  />
                }
                actionProps={{
                  alignSelf: 'center',
                }}>
              <Alert.Icon />
              <Alert.Title flexShrink={1}>{this.state.warnText}</Alert.Title>
            </Alert>
          </Collapse>
          <CommonHead
            leftItem={() => this.renderLeft()}
            titleItem={() => this.renderTitle()}
            navBarColor={'#f2f2f2'}
          />
          <VStack space={4} marginTop={4} alignItems={'center'}>
            <Input
              variant="rounded"
              width={'90%'}
              placeholder="输入名称"
              _light={{
                placeholderTextColor: 'blueGray.400',
              }}
              _dark={{
                placeholderTextColor: 'blueGray.50',
              }}
              onChangeText={text => {
                this.inputName(text);
              }}
              value={this.state.name}
            />
            <Input
                variant="rounded"
                width={'90%'}
              placeholder="输入数量"
                keyboardType={"numeric"}
              _light={{
                placeholderTextColor: 'blueGray.400',
              }}
              _dark={{
                placeholderTextColor: 'blueGray.50',
              }}
              onChangeText={text => {
                const newText = CheckPositive(text);
                this.inputNum(newText);
              }}
              value={this.state.num}
            />
            <Input
                variant="rounded"
                width={'90%'}
              placeholder="输入理想价格"
                keyboardType={"numeric"}
              _light={{
                placeholderTextColor: 'blueGray.400',
              }}
              _dark={{
                placeholderTextColor: 'blueGray.50',
              }}
              onChangeText={text => {
                const newText = CheckPrice(text);
                this.inputPrice(newText);
              }}
              value={this.state.idealPrice}
            />
            <TextArea
              value={this.state.description}
              onChangeText={text => {
                this.inputDescription(text);
              }}
              style={{
                borderColor: '#dddddd',
                borderWidth: 0.9,
                width: '90%'
              }}
              _focus={{
                borderWidth: 1,
                width: '90%'
              }}
              aria-label="t1"
              numberOfLines={4}
              placeholder="输入描述"
              isInvalid
              _dark={{
                placeholderTextColor: 'gray.300',
              }}
            />
            <Select
              variant="rounded"
              width={'90%'}
              selectedValue={this.state.label1}
              minWidth={200}
              accessibilityLabel="选择物品种类"
              placeholder="选择物品种类"
              onValueChange={itemValue => this.setLabel1(itemValue)}
              _selectedItem={{
                bg: 'cyan.600',
                endIcon: <CheckIcon size={4} />,
              }}>
              <Select.Item label="服饰装扮" value="服饰装扮" />
              <Select.Item label="酒水零食" value="酒水零食" />
              <Select.Item label="电子产品" value="电子产品" />
              <Select.Item label="游戏道具" value="游戏道具" />
              <Select.Item label="纸质资料" value="纸质资料" />
              <Select.Item label="生活用品" value="生活用品" />
              <Select.Item label="其他" value="其他" />
            </Select>
            <Select
              variant="rounded"
              width={'90%'}
              selectedValue={this.state.label2}
              minWidth={200}
              accessibilityLabel="接受崭新程度"
              placeholder="接受崭新程度"
              onValueChange={itemValue => this.setLabel2(itemValue)}
              _selectedItem={{
                bg: 'cyan.600',
                endIcon: <CheckIcon size={4} />,
              }}>
              <Select.Item label="全新未用" value="全新未用" />
              <Select.Item label="几乎全新" value="几乎全新" />
              <Select.Item label="轻微磨损" value="轻微磨损" />
              <Select.Item label="中度磨损" value="中度磨损" />
              <Select.Item label="其他" value="其他" />
            </Select>
            <Button
                backgroundColor={'#ff5002'}
                width={'90%'}
                onPress={this.edit}
                disable={this.state.disable}
            >
              {this.state.buttonText}
            </Button>
          </VStack>
        </ScrollView>
      </NativeBaseProvider>
    );
  }
}
