import React from 'react';
import storage from '../../config/Storage';
import {DeviceEventEmitter, TouchableOpacity} from "react-native";
import {
  Input,
  Button,
  NativeBaseProvider,
  FormControl,
  VStack,
  Center,
  Image,
  Box,
  Text,
} from 'native-base';
import {Login} from '../../service/UserService';
import MD5 from 'react-native-md5';

export function LoginTest({route, navigation}) {
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [errMsg, setErrMsg] = React.useState('');
  const [invalid, setInvalid] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const loginCallback = msg => {
    console.warn(msg.data);
    setLoading(false);
    if (msg.status > 0) {
      setInvalid(false);
      storage
        .save({
          key: 'loginState',
          data: {
            token: msg.data.token,
            userid: msg.data.userid,
          },
        })
        .then(r => {
          console.log(r);
        });
      route.params.updateIsLogin(true);
      DeviceEventEmitter.emit("login", msg.data.userid);
    } else {
      setInvalid(true);
      setErrMsg(msg.message);
      route.params.updateIsLogin(false);
    }
  };

  const login = () => {
    console.log('on pressed signup');
    if (email.length === 0 || password.length === 0) {
      setInvalid(true);
      setErrMsg('请填写登录信息！');
      return;
    }

    if (!/^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@(sjtu.edu.cn)/.test(email)) {
      setInvalid(true);
      setErrMsg('邮箱格式错误 ！');
      return;
    }

    setLoading(true);
    setTimeout(() => {
      setLoading(false);
    }, 3000);
    let md5password = MD5.hex_md5(password);
    console.log('on pressed login');
    let data = {
      email: email,
      password: md5password,
    };
    Login(data, loginCallback);
  };

  return (
    <NativeBaseProvider>
      <VStack>
        <Center height={280} marginTop={10}>
          <Image
            rounded={110}
            source={require('./icon.jpg')}
            alt="Welcome To PassThemOn"
            size={220}
          />
        </Center>
        <Center
          height={20}
          space={4}
          alignItems={'center'}
          marginTop={2}
          marginBottom={10}>
          <FormControl isInvalid={invalid} width={300} height={20}>
            <Input
              variant={'underlined'}
              type={'text'}
              placeholder="SJTU-Email"
              _light={{
                placeholderTextColor: 'blueGray.400',
              }}
              _dark={{
                placeholderTextColor: 'blueGray.50',
              }}
              onChangeText={text => {
                setEmail(text);
                setInvalid(false);
              }}
            />
            <Input
              variant={'underlined'}
              type={'password'}
              placeholder="Password"
              _light={{
                placeholderTextColor: 'blueGray.400',
              }}
              _dark={{
                placeholderTextColor: 'blueGray.50',
              }}
              onChangeText={text => {
                setPassword(text);
                setInvalid(false);
              }}
            />
            <FormControl.ErrorMessage>{errMsg}</FormControl.ErrorMessage>
          </FormControl>
        </Center>
        <Center height={20}>
          <Button
            backgroundColor={'#f07000'}
            isLoading={loading}
            onPress={login}
            size={'md'}
            width={'24'}
            rounded={'20'}>
            登录
          </Button>
        </Center>
        <Center>
          <TouchableOpacity onPress={()=>{navigation.navigate('reset')}}>
            <Text
                italic underline
                style={{color: '#f07000', fontSize: 14,}}
            >
              忘记密码？点这里
            </Text>
          </TouchableOpacity>
        </Center>
      </VStack>
    </NativeBaseProvider>
  );
}
