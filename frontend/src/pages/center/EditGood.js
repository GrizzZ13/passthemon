import React from 'react';
import {
  NativeBaseProvider,
  ScrollView,
  View,
  TextArea,
  Button,
  Input,
  HStack,
  VStack,
  Select,
  CheckIcon,
  Alert,
  IconButton,
  CloseIcon,
  Collapse,
  Pressable,
  Text,
} from 'native-base';
import {launchCamera, launchImageLibrary} from 'react-native-image-picker';
import {CheckPrice, CheckPositive} from '../../utils/TextUtil';
import {getGoodsById} from '../../service/GoodsService';
import {editGood} from '../../service/GoodsService';
import CommonHead from '../../common/CommonHead';
import {ImageWrapBase64} from '../../components/ImageWrapBase64';
import Spinner from 'react-native-loading-spinner-overlay';
import Icon from 'react-native-vector-icons/FontAwesome';
import {getTag} from "../../utils/GoodUtil";
import {getTypeConstant,getWearConstant} from "../../utils/GoodUtil";
import storage from "../../config/Storage";
import {Platform} from "react-native";
import {requestCameraPermission} from "../../permission/PermissionRequest";
export class EditGood extends React.Component {
  constructor() {
    super();
  }
  state = {
    images: [],
    description: '', //商品描述
    inventory: '',
    name: '',
    price: '',
    goodId: 0,
    label1: '',
    label2: '',
    loadingState: true,
    show: false,
    disable: false,
    buttonText: '修改商品',
    warn:false,
    warnText:'',
    userid:0,
  };
  //从图库添加图片
  addPhoto = () => {
    if(this.state.images.length>8){
      this.setState({
            warn:true,
            warnText:'上传图片已达上限',
          }
      );
      return;
    }else{
      this.setState({
        warn:false,
      })
    }
    launchImageLibrary(
      {
        mediaType: 'photo',
        selectionLimit: 0,
        includeBase64: true,
        maxWidth:750,
        maxHeight:750,
      },
      response => {
        if (response.assets === undefined) {
          return;
        }
        let imgs = this.state.images;
        if((this.state.images.length+response.assets.length)>9){
          this.setState({
            warn:true,
            warnText:'选择的图片太多啦',
          });
          return;
        }
        for (let i = 0; i < response.assets.length; i++) {
          imgs.push(response.assets[i].base64);
        }
        // this.setState({takeImage: response.assets});
        this.setState({images: imgs});
      },
    );
  };
  //直接拍照
  takePhoto = () => {
    if(this.state.images.length>8){
      this.setState({
            warn:true,
            warnText:'上传图片已达上限',
          }
      );
      return;
    }else{
      this.setState({
        warn:false,
      })
    }
    launchCamera(
      {
        mediaType: 'photo',
        cameraType: 'back',
        includeBase64: true,
        maxWidth:750,
        maxHeight:750,
      },
      response => {
        if (response.assets === undefined) {
          return;
        }
        let imgs = this.state.images;
        for (let i = 0; i < response.assets.length; i++) {
          imgs.push(response.assets[i].base64);
        }
        // this.setState({takeImage: response.assets});
        this.setState({images: imgs});
      },
    );
  };
  reChoose = () => {
    this.setState({images: []});
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
  removeImage = index => {
    let imgs = this.state.images;
    imgs.splice(index, 1);
    this.setState({images: imgs});
  };
  renderImagesWrap = () => {
    return (
      <HStack
        style={{
          //    确定主轴的方向
          flexDirection: 'row',
          //    一行显示不完的话换行显示
          flexWrap: 'wrap',
        }}>
        {this.renderImages(this.state.images)}
      </HStack>
    );
  };
  renderImages = images => {
    if (images === undefined) {
      return null;
    }
    let images_array = [];
    images.map((item, index) => {
      images_array.push(
        <View
          key={index}
          style={{
            alignItems: 'center',
            width: 110,
            height: 110,
            marginLeft: 10,
            marginTop: 10,
          }}>
          {/*<Image style={{width: 150, height: 150}} source={{uri: item.uri}} />*/}
          <ImageWrapBase64
            removeImage={this.removeImage}
            index={index}
            base64={item}
          />
        </View>,
      );
    });
    return images_array;
  };
  componentDidMount() {
    this.setState({goodId: this.props.route.params.goodId});
    storage.load({key: 'loginState'})
        .then(ret => {
          this.setState({userid: ret.userid});
        });
    const callback = data => {
      this.setState({
        images: data.images,
        description: data.description,
        price: data.price.toString(),
        name: data.name,
        inventory: data.inventory.toString(),
        label1: getTag(data.category),
        label2: getTag(data.attrition),
        loadingState: false,
      });
    };
    let param = {
      goodId: this.props.route.params.goodId,
    };
    //3秒之后干这个事
    setTimeout(() => {
      this.setState({
        loadingState: false,
      });
    }, 3000);
    getGoodsById(param, callback);
    if(Platform.OS==='android') {
      requestCameraPermission().then(r =>{});
    }
  }
  //头部中间组件
  renderTitle() {
    return (
      <Text mr={'10'} fontFamily={'SimSun'} fontSize={'xl'}>
        编辑商品信息
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
  inputInventory = value => {
    this.setState({inventory: value});
  };
  //输入价格
  inputPrice = value => {
    this.setState({price: value});
  };
  //输入商品名
  inputName = value => {
    this.setState({name: value});
  };
  //修改商品
  editGood = () => {
    if (
        this.state.label1 === '' ||
        this.state.label2 === '' ||
        this.state.price === '' ||
        this.state.inventory === '' ||
        this.state.description === '' ||
        this.state.name === ''||this.state.images.length===0
    ) {
      this.setState({
        warn: true,
        warnText:'请将信息补充完整',
      });
      return;
    }
    this.setState({disable: true, buttonText: '正在上传修改'});
    let labels = [];
    if (this.state.label1 !== '') {
      labels.push(getTypeConstant(this.state.label1));
    }
    if (this.state.label2 !== '') {
      labels.push(getWearConstant(this.state.label2));
    }
    //console.warn(this.state);
    let data = {
      goodId: this.state.goodId,
      name: this.state.name,
      inventory: this.state.inventory,
      price: this.state.price,
      description: this.state.description,
      images: this.state.images,
      labels: labels,
      userId:this.state.userid,
    };
    const callback = data => {
      if(data!==undefined){
      this.setState({
        show: true,
        disable: false,
        buttonText: '修改商品',
      });}
      else {
        this.setState({
          warn:true,
          disable:true,
          warnText:'权限不足，无法修改',
          buttonText:'权限不足，无法修改',
        })
      }
    };
    editGood(data, callback);
  };
  render() {
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
            <Alert.Title flexShrink={1}>修改成功,请等待审核结果</Alert.Title>
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
        <Spinner
          visible={this.state.loadingState}
          textContent={'Loading...'}
          textStyle={{
            color: '#FFF',
          }}
        />
        <ScrollView>
          <CommonHead
            leftItem={() => this.renderLeft()}
            titleItem={() => this.renderTitle()}
            navBarColor={'#f2f2f2'}
          />
          <VStack space={4} w="100%" alignItems={'center'}>
            <TextArea
                style={{
                  borderColor: '#dddddd',
                  borderWidth: 0.9,
                }}
                width={'90%'}
                value={this.state.description}
                onChangeText={text => {
                  this.inputDescription(text);
                }}
                aria-label="t1"
                numberOfLines={4}
                placeholder="输入描述"
                isInvalid
                _dark={{
                  placeholderTextColor: 'gray.300',
                }}
            />
            {this.renderImagesWrap()}
            <HStack space={4} alignItems={'center'}>
              <Button
                  backgroundColor={'#fa7900'}
                  onPress={this.addPhoto}
                  size={'md'}
                  width={'24'}
                  rounded={'20'}>
                打开图库
              </Button>
              <Button
                  backgroundColor={'#fa7900'}
                  onPress={this.takePhoto}
                  size={'md'}
                  width={'24'}
                  rounded={'20'}>
                打开相机
              </Button>
              <Button
                  backgroundColor={'#fa7900'}
                  onPress={this.reChoose}
                  size={'md'}
                  width={'24'}
                  rounded={'20'}>
                重新选择
              </Button>
            </HStack>
            <Input
                w="90%"
              value={this.state.name}
              variant="rounded"
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
            />
            <Input
                w="90%"
              variant="rounded"
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
              value={this.state.inventory}
            />
            <Input
                w="90%"
              value={this.state.price}
              variant="rounded"
                keyboardType={"numeric"}
              placeholder="输入价格"
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
            />
            <Select
                variant="rounded"
                w="90%"
              selectedValue={this.state.label1}
              minWidth={200}
              accessibilityLabel="选择物品种类"
              placeholder="选择物品种类"
              value={this.state.label1}
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
              w="90%"
              selectedValue={this.state.label2}
              value={this.state.label2}
              minWidth={200}
              accessibilityLabel="崭新程度"
              placeholder="崭新程度"
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
                onPress={this.editGood}
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
