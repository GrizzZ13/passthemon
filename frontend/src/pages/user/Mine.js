import React from 'react';
import {Alert, DeviceEventEmitter} from 'react-native';
import {
  Dimensions,
  Text,
  View,
  Image,
  ScrollView,
  TouchableOpacity,
  Animated,
  Easing,
} from 'react-native';
import storage from '../../config/Storage';
import {getUserInfoByUserId} from '../../service/UserService';
import {ListItem, styles} from '../../common/ListItem';
import {ADD_DEMAND, ADD_GOODS, REMOVE_DEMAND, REMOVE_GOODS} from "../../config/UserConstant";
import {deleteWants} from "../../service/WantsService";

const {width: screenWidth, height: screenHeight} = Dimensions.get('window');

export default class Mine extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      rotation: new Animated.Value(0),
      scale: new Animated.Value(1),
      translateY: new Animated.Value(10),
      opacity: new Animated.Value(0),
      name: '',
      credit: 0,
      favoriteNum: 0,
      goodsNum: 0,
      demandsNum: 0,
      buyNum: 0,
      sellNum: 0,
      gender: 3,
      image: '',
      userid: 0,
    };
  }
  componentDidMount() {
    //设置监听
    this.listener = DeviceEventEmitter.addListener('EditUser', message => {
      if(message.type===1) {
        this.setState({
          name: message.name,
          image: message.image,
          gender: message.gender,
        });
      }
      else if(message.type===ADD_GOODS){
        this.setState({goodsNum:this.state.goodsNum+1});
      }
      else if(message.type===ADD_DEMAND){
        this.setState({demandsNum:this.state.demandsNum+1});
      }
      else if(message.type===4){
        this.setState({sellNum:this.state.sellNum+1});
      }
      else if(message.type===5){
        this.setState({buyNum:message.len})
      }
      else if(message.type===REMOVE_GOODS){
        this.setState({goodsNum:this.state.goodsNum-1})
      }
      else if(message.type===REMOVE_DEMAND){
        this.setState({demandsNum:this.state.demandsNum-1});
      }
    });
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
      this.setState({
        name: data.name,
        credit: data.credit,
        favoriteNum: data.favoriteNum,
        goodsNum: data.goodsNum,
        demandsNum: data.demandsNum,
        buyNum: data.buyNum,
        sellNum: data.sellNum,
        gender: data.gender,
        image: data.image,
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
        getUserInfoByUserId(param, callback);
      });
  }
  componentWillUnmount() {
    //移除监听
    if (this.listener) {
      this.listener.remove();
    }
  }
  renderGender = () => {
    if (this.state.gender === 1) {
      return (
        <Image
          style={styles.positionImg}
          source={require('./male.png')}
          alt={' '}
        />
      );
    } else if (this.state.gender === 2) {
      return (
        <Image
          style={styles.positionImg}
          source={require('./female.png')}
          alt={' '}
        />
      );
    } else {
      return (
        <Image
          style={styles.positionImg}
          source={require('./unknow.png')}
          alt={' '}
        />
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
              alt={' '}
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
  render() {
    const {navigation, route} = this.props;
    return (
      <ScrollView style={styles.container}>
        {/* 头部 */}
        <View style={styles.headContainer}>
          {/* 夜间/签到 */}
          <View style={styles.headTopContainer}>
            <TouchableOpacity
              style={styles.topBtnStyle}
              activeOpacity={0.9}
              onPress={() => {
                this.props.navigation.goBack();
              }}>
              <Image
                source={require('./left_arrow.png')}
                alt={' '}
                style={styles.headTopImg}
                resizeMode={'contain'}
              />
              <Text style={styles.headTopText}>返回</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.topBtnStyle}
              activeOpacity={0.9}
              onPress={() => {
                this.props.navigation.navigate('EditUser');
              }}>
              <Image
                source={require('./i_sign.png')}
                alt={' '}
                style={styles.headTopImg}
                resizeMode={'contain'}
              />
              <Text style={styles.headTopText}>编辑</Text>
            </TouchableOpacity>
          </View>

          {/* 头像、昵称、标签 */}
          {this.renderHead()}

          {/* 收藏、历史、更贴 */}
          <View style={styles.headBottomContainer}>
            <TouchableOpacity
              style={styles.bottomBtn}
              activeOpacity={1}
              onPress={() => {
                this.props.navigation.navigate('MyGoods',{userid:this.state.userid});
              }}>
              <Text style={styles.bottomNum}>{this.state.goodsNum}</Text>
              <Text style={styles.bottomText}>发布的商品</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.bottomBtn}
              activeOpacity={1}
              onPress={() => {
                this.props.navigation.navigate('MyDemand',{userid:this.state.userid});
              }}>
              <Text style={styles.bottomNum}>{this.state.demandsNum}</Text>
              <Text style={styles.bottomText}>发布的需求</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.bottomBtn}
              activeOpacity={1}
              onPress={() => {
                navigation.navigate('MyOrders', {
                  opt: 1,
                });
              }}>
              <Text style={styles.bottomNum}>{this.state.sellNum}</Text>
              <Text style={styles.bottomText}>我卖出的</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.bottomBtn}
              activeOpacity={1}
              onPress={() => {
                navigation.navigate('MyOrders', {
                  opt: 0,
                });
              }}>
              <Text style={styles.bottomNum}>{this.state.buyNum}</Text>
              <Text style={styles.bottomText}>我买入的</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* 过渡条 */}
        <View style={styles.transitionView} />

        {/* 设置列表 */}
        <View style={styles.settingListContainer}>
          <ListItem
            key={0}
            leftText={'浏览历史'}
            rightText={'查看浏览历史'}
            onPress={() => {
              this.props.navigation.navigate('HistoryScreen');
            }}
          />
          <ListItem
            key={1}
            leftText={'我的关注'}
            onPress={() => {
              this.props.navigation.navigate('MyFollows', {
                userid: this.state.userid,
                username:this.state.name,
              });
            }}
          />
          <ListItem
              key={2}
              leftText={'我的收藏'}
              onPress={() => {
                this.props.navigation.navigate('FavoriteScreen');
              }}
          />
          <ListItem
              key={3}
              leftText={'退出登录'}
              onPress={() => {
                Alert.alert('提示','确认退出登录？',[
                  {text:'确认',onPress:() => {
                      this.props.navigation.navigate('Logout')} },
                  {text:'取消',style:'cancel'}
                ],{cancelable:false});
              }}
          />
        </View>
      </ScrollView>
    );
  }
}
