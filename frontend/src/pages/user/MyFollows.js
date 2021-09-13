import React from 'react';
import {
  Box,
  Center,
  FlatList,
  NativeBaseProvider,
  Text,
} from 'native-base';
import {TouchableOpacity, View} from 'react-native';
import {getUsersByPage} from '../../service/UserService';
import {UserListItem} from '../../common/UserListItem';
import {styles} from '../../common/ListItem';
import {Image} from 'react-native';
import CommonHead from '../../common/CommonHead';

export class MyFollows extends React.Component {
  constructor(props) {
    super(props);
  }

  state = {
    followsData: [],
    fetchPage: 0,
    userid: 0,
    isEnd: false,
  };
  componentDidMount() {
    //不从内存拿userid了，直接从主页传过来
    this.setState({userid: this.props.route.params.userid});
    const callback = data => {
      if (data.list.length === 0) {
        this.setState({isEnd: true});
      }
      this.setState({
        followsData: data.list,
      });
    };
    let param = {
      userId: this.props.route.params.userid,
      fetchPage: this.state.fetchPage,
    };
    getUsersByPage(param, callback);
  }

  renderImage = image => {
    if (image === undefined || image === null) {
      return <Image source={require('./i_user.jpg')} />;
    } else {
      return <Image source={{uri: 'data:image/jpg;base64,' + image}} />;
    }
  };

  //头部中间组件
  renderTitle() {
    return (
      <Center paddingRight={16}>
        <Text style={{
          fontSize: 16,
          height: 40,
          textAlign:'center',
          justifyContent: 'center',
          paddingTop: "2%",
        }}>
          我的关注
        </Text>
      </Center>
    );
  }

  renderLeft() {
    return(
        <Box ml={2}>
          <TouchableOpacity
              style={{
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                width: 70,
                height: 30,
                borderColor: '#e6e6e6',
                borderWidth: 1,
                borderRadius: 20,
              }}
              activeOpacity={0.9}
              onPress={() => {
                this.props.navigation.goBack();
              }}>
            <Image
                source={require('../../asset/img/left_arrow.png')}
                style={{
                  width: 15,
                  height: 15,
                  marginRight: 5,
                }}
                resizeMode={'contain'}
                alt={' '}
            />
            <Text style={{
              fontSize: 14,
              color: '#515151',
            }}>返回</Text>
          </TouchableOpacity>
        </Box>
    )
  }

  renderWhenEmpty = () => {
    return (
      <Center>
        <Text style={{color : '#c0c0c0', fontSize : 16}}>您还没有关注其他用户</Text>
      </Center>
    );
  };
  getMoreFollows=()=>{
      const callback = data => {
          console.warn(data.list.length);
          if (data.list.length === 0) {
              this.setState({
                  isEnd: true,
              });
          } else {
              this.setState({
                  fetchPage: this.state.fetchPage + 1,
                  followsData: this.state.followsData.concat(data.list),
              });
          }
      };
          if (this.state.isEnd === false) {
              let param = {
                  fetchPage: this.state.fetchPage + 1,
                  userId: this.state.userid,
              };
              getUsersByPage(param, callback);
          }

  }

  render() {
    return (
      <NativeBaseProvider>
        <CommonHead
          leftItem={() => this.renderLeft()}
          titleItem={() => this.renderTitle()}
        />
        <View style={styles.transitionView} />
        <FlatList
          data={this.state.followsData}
          numColumns={1}
          ListEmptyComponent={this.renderWhenEmpty()}
          mx="1%"
          onEndReachedThreshold={0}
          onEndReached={this.getMoreFollows}
          renderItem={({item}) => (
            <UserListItem
              key={item.userid}
              username={item.username}
              image={item.image}
              userid={item.userid}
              authorUsername={this.props.route.params.username}
            />
          )}
          keyExtractor={item => item.userid}
        />
      </NativeBaseProvider>
    );
  }
}
