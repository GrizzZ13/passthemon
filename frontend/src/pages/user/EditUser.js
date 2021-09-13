import React from 'react'
import ImagePicker from 'react-native-image-crop-picker';
import {DeviceEventEmitter} from 'react-native';
import storage from '../../config/Storage';
import {getUserByUserId, submitChange} from '../../service/UserService';
import {
  Dimensions,
  StyleSheet,
  Text,
  View,
  Image,
  ScrollView,
  TouchableOpacity,
  Animated,
  Easing,
} from 'react-native';
import {
  NativeBaseProvider,
  HStack,
  Select,
  Alert,
  Input,
  CheckIcon,
  IconButton,
  CloseIcon,
  Collapse,
  Button,
} from 'native-base';
import BackHeader from "../../common/BackHeader";
const {width: screenWidth, height: screenHeight} = Dimensions.get('window');

export default class EditUser extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      rotation: new Animated.Value(0),
      scale: new Animated.Value(1),
      translateY: new Animated.Value(10),
      opacity: new Animated.Value(0),
      image: '', //用base64存储的图片
      name: '',
      phone: '',
      gender: 'unknown',
      userid: 0,
      show:false,
      disable:false, //按钮是否禁用
      buttonText:'提交修改',
      warn:false,
      warnText:'',
    };
  }
  selectHeadPhoto = () => {
    ImagePicker.openPicker({
      width:500,
      height:500,
      cropping:true,
      includeBase64:true,
    }).then(image=>{
      this.setState({
        image:image.data,
      })
    });
  };
  renderGender = () => {
    if (this.state.gender === 'male') {
      return (
          <Image style={styles.positionImg} source={require('./male.png')} />
      );
    } else if (this.state.gender === 'female') {
      return (
          <Image style={styles.positionImg} source={require('./female.png')} />
      );
    } else {
      return (
          <Image style={styles.positionImg} source={require('./unknow.png')} />
      );
    }
  };
  renderHead = () => {
    if (this.state.image === '' || this.state.image === undefined) {
      return (
          <View style={styles.headContainer}>
            <View style={styles.headCenterContainer}>
              <Animated.Image
                  style={[
                    styles.userImg,
                    {
                      transForm: [
                        {
                          rotateY: this.state.rotation.interpolate({
                            inputRange: [0, 1],
                            outputRange: ['0deg', '360deg'],
                          }),
                        },
                        {
                          scale: this.state.scale,
                        },
                      ],
                    },
                  ]}
                  source={require('./i_user.jpg')}
                  resizeMode={'contain'}
              />
              <Text>{this.state.name}</Text>
              <View style={styles.positionContainer}>
                {this.renderGender()}
                <Text style={styles.positionText}>
                  {'信誉分:' + this.state.credit}
                </Text>
              </View>
            </View>
          </View>
      );
    } else {
      return (
          <View style={styles.headContainer}>
            <View style={styles.headCenterContainer}>
              <Animated.Image
                  style={[
                    styles.userImg,
                    {
                      transForm: [
                        {
                          rotateY: this.state.rotation.interpolate({
                            inputRange: [0, 1],
                            outputRange: ['0deg', '360deg'],
                          }),
                        },
                        {
                          scale: this.state.scale,
                        },
                      ],
                    },
                  ]}
                  source={{uri: 'data:image/jpg;base64,' + this.state.image}}
                  resizeMode={'contain'}
              />
              <Text>{this.state.name}</Text>
              <View style={styles.positionContainer}>
                {this.renderGender()}
                <Text style={styles.positionText}>
                  {'信誉分:' + this.state.credit}
                </Text>
              </View>
            </View>
          </View>
      );
    }
  };
  inputName = value => {
    this.setState({name: value});
  };
  inputPhone = value => {
    this.setState({phone: value});
  };
  setGender = value => {
    this.setState({gender: value});
  };
  componentDidMount() {
    //顺序执行
    Animated.sequence([
      //随着时间发展执行
      Animated.timing(this.state.rotation, {
        toValue: 1,
        duration: 500,
        easing: Easing.linear,
      }),
      Animated.timing(this.state.scale, {
        toValue: 1.3,
        duration: 600,
      }),
      //同时执行
      Animated.parallel([
        Animated.timing(this.state.scale, {
          toValue: 1,
          duration: 500,
        }),
        Animated.timing(this.state.opacity, {
          toValue: 1,
          duration: 1000,
        }),
        Animated.timing(this.state.translateY, {
          toValue: 0,
          duration: 600,
        }),
      ]),
    ]);
    const callback = data => {
      let text = '';
      if (data.gender === 3)
      {
        text = 'unknown';
      }
      else if (data.gender === 2)
      {
        text = 'female';
      }
      else
      {
        text = 'male';
      }
      this.setState({
        name: data.username,
        phone: data.phone,
        image: data.image,
        gender: text,
        credit: data.credit,
      });
    };
    storage
        .load({key: 'loginState'})
        .then(ret => {
          this.setState({userid: ret.userid});
          return ret.userid;
        })
        .then(ret => {
          const param = {
            userId: ret,
          };
          getUserByUserId(param, callback);
        });
  }
  submitChange = () => {
    this.setState(
        {
          show:false,
          disable:true,
          buttonText:'正在提交',
        }
    );
    let type = 3;
    if (this.state.gender === 'male') {
      type = 1;
    } else if (this.state.gender === 'female') {
      type = 2;
    }
    const callback = data => {
      if(data!==undefined){
        this.setState(
            {
              show:true,
              disable:false,
              buttonText:'提交修改',
              warn:false
            }
        );
      }
      else {
        this.setState({
              show:false,
              warn:true,
              warnText:'权限不足，无法操作',
              disable:true,
              buttonText:'权限不足，无法操作',
            }
        );
      }
    };
    const param = {
      type:1,
      userId: this.state.userid,
      name: this.state.name,
      phone: this.state.phone,
      image: this.state.image,
      gender: type,
    };
    DeviceEventEmitter.emit('EditUser', param);
    submitChange(param, callback);
  };
  render() {
    console.log(this.state.phone);
    return (
        <NativeBaseProvider>
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
              <Alert.Title flexShrink={1}>修改成功</Alert.Title>
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
          <BackHeader text={'编辑信息'} backgroundColor={"#f8f8f8"}/>
          <ScrollView style={styles.container}>
            {this.renderHead()}
            <View style={styles.transitionView} />
            <View style={styles.settingListContainer}>
              <ListItem
                  key={0}
                  leftText={'编辑头像'}
                  rightText={'从图库选择'}
                  isShowArrow={true}
                  onPress={this.selectHeadPhoto}
              />
              <ListItem
                  key={1}
                  leftText={'编辑昵称'}
                  isShowArrow={false}
                  rightComponent={
                    <Input
                        variant={'none'}
                        placeholder={'昵称'}
                        minWidth={200}
                        onChangeText={text => this.inputName(text)}
                        value={this.state.name}
                    />
                  }
              />
              <ListItem
                  key={2}
                  leftText={'编辑电话号码'}
                  isShowArrow={false}
                  rightComponent={
                    <Input
                        variant={'none'}
                        minWidth={200}
                        placeholder={'电话'}
                        keyboardType={"numeric"}
                        onChangeText={text => this.inputPhone(text)}
                        value={this.state.phone}
                    />
                  }
              />
              <ListItem
                  key={3}
                  leftText={'编辑性别'}
                  isShowArrow={false}
                  rightComponent={
                    <Select
                        variant="none"
                        selectedValue={this.state.gender}
                        minWidth={200}
                        bgColor={'#f8f8f8'}
                        accessibilityLabel="选择性别"
                        placeholder="选择"
                        onValueChange={itemValue => this.setGender(itemValue)}
                        value={this.state.gender}
                        _selectedItem={{
                          bg: 'cyan.600',
                          endIcon: <CheckIcon size={4} />,
                        }}>
                      <Select.Item label="male" value="male" />
                      <Select.Item label="female" value="female" />
                      <Select.Item label="unknown" value="unknown" />
                    </Select>
                  }
              />
            </View>
            <View style={styles.transitionView} />
            <View
                style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
              <HStack space={10} marginTop={10}>
                <Button
                    disabled={this.state.disable}
                    onPress={this.submitChange}
                    backgroundColor={'#ff6c02'}
                    height={12}
                    width={32}
                    rounded={20}
                >
                  {this.state.buttonText}
                </Button>
                <Button
                    onPress={() => this.props.navigation.goBack()}
                    backgroundColor={'#ff6c02'}
                    height={12}
                    width={32}
                    rounded={20}
                >
                  返回上页
                </Button>
              </HStack>
            </View>
          </ScrollView>
        </NativeBaseProvider>
    );
  }
}

class ListItem extends React.PureComponent {
  static defaultProps = {
    leftText: '',
    rightText: '',
    isShowUnderline: true,
    isShowArrow: true,
  };

  _renderRight = () => {
    if (!this.props.rightText && !this.props.rightComponent) {
      return <Text />;
    }

    if (this.props.rightText) {
      return <Text style={styles.itemRightText}>{this.props.rightText}</Text>;
    }

    if (this.props.rightComponent) {
      return this.props.rightComponent;
    }
  };

  render() {
    return (
        <TouchableOpacity
            activeOpacity={0.9}
            style={[
              styles.itemContainer,
              this.props.isShowUnderline && styles.itemBorderBottom,
            ]}
            onPress={this.props.onPress}>
          <Text style={styles.itemLeftText}>{this.props.leftText}</Text>

          <View style={styles.itemRightContainer}>
            {this._renderRight()}
            {!this.props.rightComponent && this.props.isShowArrow && (
                <Image
                    style={styles.itemRightImg}
                    source={require('./i_right.png')}
                />
            )}
          </View>
        </TouchableOpacity>
    );
  }
}

const styles = StyleSheet.create({
  itemContainer: {
    flexDirection: 'row',
    backgroundColor: '#f8f8f8',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: 50,
  },
  itemBorderBottom: {
    borderBottomWidth: 1,
    borderBottomColor: '#e6e6e6',
  },
  itemLeftText: {
    fontSize: 14,
    color: '#000',
  },
  itemRightContainer: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  itemRightText: {
    color: '#bfbfbf',
    fontSize: 12,
  },
  itemRightImg: {
    width: 20,
    height: 20,
    marginHorizontal: 7,
  },
  container: {
    flex: 1,
    backgroundColor: '#F8F8F8',
  },
  headContainer: {
    paddingHorizontal: 15,
    paddingBottom: 10,
    paddingTop: 30,
  },
  headTopContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
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
  headTopImg: {
    width: 15,
    height: 15,
    marginRight: 5,
  },
  headTopText: {
    fontSize: 12,
    color: '#515151',
  },
  headCenterContainer: {
    alignItems: 'center',
    marginBottom: 15,
  },
  userImg: {
    width: 150,
    height: 150,
    borderRadius: 100,
  },
  userNickname: {
    marginVertical: 5,
    fontSize: 20,
    color: '#000',
  },
  positionContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  positionImg: {
    width: 10,
    height: 10,
    marginRight: 2,
  },
  positionText: {
    color: '#bfbfbf',
    fontSize: 10,
  },
  headBottomContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  bottomBtn: {
    alignItems: 'center',
  },
  bottomNum: {
    fontSize: 20,
    color: '#000',
  },
  bottomText: {
    color: '#bfbfbf',
    fontSize: 12,
  },
  transitionView: {
    height: 10,
    backgroundColor: 'rgba(230,230,230, .5)',
  },
  settingListContainer: {
    paddingLeft: 20,
  },
});
