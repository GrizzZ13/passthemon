import React from 'react';
import {
  Platform,
  Dimensions,
  StyleSheet,
  Text,
  View,
  Image,
  Alert,
  ScrollView,
  TouchableOpacity,
  Animated,
  Easing,
} from 'react-native';
import {followOther, getUserNameById, unFollowOther} from '../../service/UserService';
import {ListItem} from '../../common/ListItem';
import {styles} from '../../common/ListItem';
import {getOtherInfo, getUserInfoByUserId} from '../../service/UserService';
import storage from '../../config/Storage';
import NavigationService from "../../service/NavigationService";
const {width: screenWidth, height: screenHeight} = Dimensions.get('window');
export default class Other extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      rotation: new Animated.Value(0),
      scale: new Animated.Value(1),
      translateY: new Animated.Value(10),
      opacity: new Animated.Value(0),
      userid: 0, //别人的userid
      myId: 0, //我的Id
      name: '', //别人的名字
      authorUsername:'',//我的名字
      gender: 3,
      goodsNum: 0,
      demandsNum: 0,
      sellNum:0,
      image: '',
      credit: 0,
      followId: -1,
    };
  }

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
    this.setState({userid: this.props.route.params.userid});
    const callback = data => {
      this.setState({
        name: data.name,
        gender: data.gender,
        goodsNum: data.goodsNum,
        demandsNum: data.demandsNum,
        sellNum:data.sellNum,
        image: data.image,
        credit: data.credit,
        followId: data.followId,
      });
    };
    const callback2 = data =>{
      this.setState(
          {authorUsername:data.username}
      )
    }
    storage
      .load({key: 'loginState'})
      .then(ret => {
        this.setState({myId: ret.userid});
        return ret.userid;
      })
      .then(ret => {
        const param = {
          otherId: this.props.route.params.userid,
          userId: ret,
        };
        console.warn(param);
        const param2 = {
          userId:ret,
        }
        getOtherInfo(param, callback);
        getUserNameById(param2,callback2);
      });
  }
  Follow = () => {
    const callback = data => {
      this.setState({followId: data.followId});
      console.warn(this.state.followId);
    };
    const param = {
      otherId: this.state.userid,
      userId: this.state.myId,
    };
    followOther(param, callback);
  };
  UnFollow = () => {
    const callback = data => {
      this.setState({followId: -1});
      console.warn(this.state.followId);
    };
    const param = {
      userId:this.state.myId,
      followId: this.state.followId,
    };
    unFollowOther(param, callback);
  };
  renderfollow = () => {
    if (this.state.followId === -1) {
      return (
        <TouchableOpacity
          style={styles.topBtnStyle}
          activeOpacity={0.9}
          onPress={this.Follow}>
          <Image
            source={require('./empty_star.png')}
            alt={' '}
            style={styles.headTopImg}
            resizeMode={'contain'}
          />
          <Text style={styles.headTopText}>关注</Text>
        </TouchableOpacity>
      );
    } else {
      return (
        <TouchableOpacity
          style={styles.topBtnStyle}
          activeOpacity={0.9}
          onPress={this.UnFollow}>
          <Image
            source={require('./full_star.png')}
            alt={' '}
            style={styles.headTopImg}
            resizeMode={'contain'}
          />
          <Text style={styles.headTopText}>取关</Text>
        </TouchableOpacity>
      );
    }
  };
  renderGender = () => {
    if (this.state.gender == 1) {
      return (
        <Image
          style={styles.positionImg}
          source={require('./male.png')}
          alt={' '}
        />
      );
    } else if (this.state.gender == 2) {
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
    if (this.state.image == '' || this.state.image == undefined) {
      return (
        <View style={styles.headContainer}>
          {/* 夜间/签到 */}
          {/* 头像、昵称、标签 */}
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
          {/* 夜间/签到 */}
          {/* 头像、昵称、标签 */}
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
                style={styles.headTopImg}
                resizeMode={'contain'}
              />
              <Text style={styles.headTopText}>返回</Text>
            </TouchableOpacity>
            {this.renderfollow()}
          </View>

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
              <Text style={styles.bottomText}>他的商品</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.bottomBtn}
              activeOpacity={1}
              onPress={() => {
                this.props.navigation.navigate('MyDemand',{userid:this.state.userid});
              }}>
              <Text style={styles.bottomNum}>{this.state.demandsNum}</Text>
              <Text style={styles.bottomText}>他的需求</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.bottomBtn}
                activeOpacity={1}
                onPress={() => {
                  this.props.navigation.navigate('OtherComments',{opt:2,userid:this.state.userid});
                }}>
              <Text style={styles.bottomNum}>{this.state.sellNum}</Text>
              <Text style={styles.bottomText}>他卖出的</Text>
            </TouchableOpacity>
          </View>
        </View>

        {/* 过渡条 */}
        <View style={styles.transitionView} />

        {/* 设置列表 */}
        <View style={styles.settingListContainer}>
          <ListItem key={0} leftText={'发送消息'} onPress={()=>{
            NavigationService.navigate('ChatInside',{channelInfo:{
              authorId: this.state.myId,
              authorUsername: this.state.authorUsername,
              recipientId: this.state.userid,
              recipientUsername: this.state.name,
            }})}}/>
        </View>
      </ScrollView>
    );
  }
}
