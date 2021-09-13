//用法：<UserListItem username={} image={} onPress={}/>
//修改空间大
//
import React from 'react';
import {Box, Text, Pressable} from 'native-base';
import CommonHead from './CommonHead';
import Icon from 'react-native-vector-icons/FontAwesome';
import NavigationService from '../service/NavigationService';
import storage from "../config/Storage";
import UserIcon from "../pages/chat/UserIcon";
export class UserListItem extends React.Component {
  constructor(props) {
      super(props);
      this.state={
          authorId: '',
          authorUsername: '',
          recipientId:'',
          recipientUsername: '',
      }
  }

  componentDidMount() {
      this.setState(
          {
              recipientId:this.props.userid,
              recipientUsername:this.props.username,
              authorUsername:this.props.authorUsername,
          }
      )
      storage
          .load({key: 'loginState'})
          .then(ret => {
              this.setState({authorId: ret.userid});
          });
  }

    renderImage = () => {
      return <Pressable
          onPress={() => {
              NavigationService.navigate('Other', {userid: this.props.userid});
          }}>
          <UserIcon source={this.props.image} size={50}/>
      </Pressable>
  };

  renderTitle = () => {
      return <Text fontSize="lg" isTruncated>
          {this.props.username !== null && this.props.username !== undefined ?
              this.props.username : 'unnamed'}
      </Text>
  };

  renderRight = () => {
    return (
      <Box>
        <Pressable onPress={()=>{NavigationService.navigate('ChatInside',{channelInfo:{
            authorId: this.state.authorId,
            authorUsername: this.state.authorUsername,
            recipientId: this.state.recipientId,
            recipientUsername: this.state.recipientUsername,
            }})}} mr={'6'}>
          <Icon
            size={36}
            name="comment"
            color={'#d0d0d0'}
          />
        </Pressable>
      </Box>
    );
  };

  render() {
    return (
      <CommonHead
        leftItem={() => this.renderImage()}
        titleItem={() => this.renderTitle()}
        rightItem={() => this.renderRight()}
        borderBottomWidth={0.5}
        borderColor={'#e0e0e0'}
        navBarStyle={{height: 82}}
      />
    );
  }
}
