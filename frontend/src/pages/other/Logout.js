import React from 'react';
import storage from '../../config/Storage';
import {Center, NativeBaseProvider, Text} from 'native-base';
import {DeviceEventEmitter} from "react-native";

export class Logout extends React.Component {
  componentDidMount() {
    console.warn('logout did mount');
    this.props.route.params.updateIsLogin(false);
    storage.remove({key: 'loginState'}).then(r => {
      DeviceEventEmitter.emit('logout');
    });
  }
  render() {
    return (
      <NativeBaseProvider>
        <Center>
          <Text>用户信息过期，请重新登录</Text>
        </Center>
      </NativeBaseProvider>
    );
  }
}
