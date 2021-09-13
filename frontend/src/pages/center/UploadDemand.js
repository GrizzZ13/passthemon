import React from 'react';
import {
  NativeBaseProvider,
  ScrollView,
  TextArea,
  Button,
  Input,
  VStack,
  Select,
  CheckIcon,
  Collapse,
  Alert,
  IconButton,
  CloseIcon,
  Pressable,
  Text,
} from 'native-base';
import {CheckPrice, CheckPositive} from '../../utils/TextUtil';
import {getDatetime} from '../../utils/GoodUtil';
import storage from '../../config/Storage';
import {addDemand} from '../../service/DemandService';
import CommonHead from '../../common/CommonHead';
import Icon from 'react-native-vector-icons/FontAwesome';
import {DeviceEventEmitter} from "react-native";
import {getTypeConstant,getWearConstant} from "../../utils/GoodUtil";
export class UploadDemand extends React.Component {
  state = {
    description: '', //商品描述
    num: '',
    name: '',
    idealPrice: '',
    userid: 0, //用户id
    label1: '',
    label2: '',
    show: false, //是否展示Alert
    disable: false, //按钮是否禁用
    buttonText: '上传', //按钮上的文字
    warn: false, //是否展示警告
    warnText:'', //警告文字
  };
  reset = () => {
    this.setState({
      num: '',
      name: '',
      idealPrice: '',
      label1: '',
      label2: '',
      description: '',
    });
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
    storage.load({key: 'loginState'}).then(ret => {
      this.setState({userid: ret.userid});
    });
  }
  //头部中间组件
  renderTitle() {
    return (
        <Text mr={'10'} fontFamily={'SimSun'} fontSize={'lg'}>
          上传需求物品
        </Text>
    );
  }
  renderLeft() {
    return (
        <Pressable ml={'2'} onPress={() => this.props.navigation.goBack()}>
          <Icon size={25} name="arrow-left" backgroundColor="#a0eee1" />
        </Pressable>
    );
  }
  //输入数量
  inputInventory = value => {
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
  //上传商品
  uploadDemand = () => {
    if (
        this.state.num === '' ||
        this.state.name === '' ||
        this.state.idealPrice === '' ||
        this.state.label1 === '' ||
        this.state.label2 === '' ||
        this.state.description === ''
    ) {
      this.setState({
        warn: true,
        warnText:'请将信息补充完整',
      });
      return;
    }
    this.setState({
      disable: true, //禁用按钮
      buttonText: '正在上传', //按钮上的文字
    });
    let labels = [];
    if (this.state.label1 !== '') {
      labels.push(getTypeConstant(this.state.label1));
    }
    if (this.state.label2 !== '') {
      labels.push(getWearConstant(this.state.label2));
    }
    let datetime = getDatetime();
    let data = {
      name: this.state.name,
      num: this.state.num,
      description: this.state.description,
      upload_time: datetime,
      idealPrice: this.state.idealPrice,
      userId: this.state.userid,
      labels: labels,
    };
    const callback = data => {
      if(data!==undefined){
          let param={
              type:3,
          }
          DeviceEventEmitter.emit('EditUser', param);
        this.setState({
          show: true,
          disable: false, //按钮是否禁用
          buttonText: '上传', //按钮上的文字
          warn: false,
        });
        this.reset();}
      else{
        this.setState({
              disable:true,
              warn:true,
              warnText:'权限不足，无法上传',
              buttonText:'权限不足，无法上传',
            }
        );
      }
    };
    addDemand(data, callback);
  };
  render() {
    return (
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
                <Alert.Title flexShrink={1}>上传成功，请等待审核通过</Alert.Title>
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
            <VStack alignItems="center" space={4} marginTop={4}>
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
                    this.inputInventory(newText);
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
                  value={this.state.label1}
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
                  value={this.state.label2}
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
                  disabled={this.state.disable}
                  onPress={this.uploadDemand}
              >
                {this.state.buttonText}
              </Button>
            </VStack>
          </ScrollView>
        </NativeBaseProvider>
    );
  }
}
