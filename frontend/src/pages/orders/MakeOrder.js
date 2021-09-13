import React, {useState} from 'react';
import {makeOrder} from '../../service/OrderService';
import {StyleSheet} from 'react-native';
import {
  IconButton,
  Box,
  Heading,
  NativeBaseProvider,
  Center,
  Image,
  Text,
  HStack,
  Button,
  StatusBar,
  InfoIcon,
  ArrowBackIcon,
  Input,
  FormControl,
  ScrollView,
  Pressable,
} from 'native-base';

export function MakeOrder({route, navigation}) {
  const params = route.params.goodsInfo;
  const userId = route.params.userId;
  const [address, setAddress] = useState('');
  const [addressErrMsg, setAddressErrMsg] = useState('');
  const [addressInvalid, setAddressInvalid] = useState(true);
  const [phoneErrMsg, setPhoneErrMsg] = useState('');
  const [phoneInvalid, setPhoneInvalid] = useState(true);
  const [phoneNumber, setPhoneNumber] = useState('');


  const setPhone = text => {
    console.log(text);
    if (text === '') {
      setPhoneInvalid(true);
      setPhoneErrMsg('不能为空');
    } else if (!/^1[3456789]\d{9}$/.test(text)) {
      setPhoneInvalid(true);
      setPhoneErrMsg('手机格式有误');
    } else {
      setPhoneInvalid(false);
      setPhoneNumber(text);
    }
  };
  const _setAddress = text => {
    if (text === '') {
      setAddressInvalid(true);
      setAddressErrMsg('不能为空');
    } else {
      setAddressInvalid(false);
      setAddress(text);
    }
  }

  const _makeOrder = () => {
    // if (addressInvalid || phoneInvalid) {
    //   alert('填写信息有误');
    //   return;
    // }

    const callback = data => {
      let info = '';
      switch (data.returnVal) {
        case -1:
          info = '商品信息走丢了噢，请刷新当前界面！';
          break;
        case 0:
          info = '下单成功，请等待出库！';
          break;
        case 1:
          info = '商品库存不够，大侠少买点吧！';
          break;
        case 2:
          info = '休想刷单，不可以买自己的商品噢！';
          break;
      }
      alert(info);
    };
    makeOrder(
      {
        userId: userId,
        // address: address,
        // phone: phoneNumber,
        goodsId: params.id,
        goodsNum: 1,
      },
      callback
    );
  };


  return (
    <NativeBaseProvider>
      {/*<AppBar navigation={navigation}/>*/}
      <ScrollView>
        <Pressable
          onPress={() => alert("跳转到商品详情页")}
          p={2}
        >
          <Center style={styles.card}>
            <Heading style={styles.heading}>购物清单</Heading>
            <Image
                style={styles.bookImage}
                alt="goodsImage"
                source={{uri: 'data:image/jpg;base64,' + params.imageList[0].img}}
            />
            <Text style={styles.description}>{params.description}</Text>
          </Center>
        </Pressable>
        <Center style={styles.card}>
          <Heading style={styles.heading}>购买者信息</Heading>
          {/*<FormControl*/}
          {/*  isRequired*/}
          {/*  isInvalid={phoneInvalid}*/}
          {/*  alignItems={'center'}>*/}
          {/*  <Input*/}
          {/*    width={"58%"}*/}
          {/*    textAlign={"center"}*/}
          {/*    borderRadius={20}*/}
          {/*    borderWidth={1.2}*/}
          {/*    borderColor={"#ff7f50"}*/}
          {/*    marginTop={"1%"}*/}
          {/*    marginBottom={"2%"}*/}
          {/*    placeholder="联系电话"*/}
          {/*    _light={{*/}
          {/*      placeholderTextColor: 'blueGray.400',*/}
          {/*    }}*/}
          {/*    _dark={{*/}
          {/*      placeholderTextColor: 'blueGray.50',*/}
          {/*    }}*/}
          {/*    onChangeText={text => {*/}
          {/*      setPhone(text);*/}
          {/*    }}*/}
          {/*  />*/}
          {/*  <FormControl.ErrorMessage>{phoneErrMsg}</FormControl.ErrorMessage>*/}
          {/*</FormControl>*/}
          {/*<FormControl isRequired isInvalid={addressInvalid} alignItems={'center'}>*/}
          {/*  <Input*/}
          {/*      width={"58%"}*/}
          {/*      textAlign={"center"}*/}
          {/*      borderRadius={20}*/}
          {/*      borderWidth={1.2}*/}
          {/*      borderColor={"#ff7f50"}*/}
          {/*      marginTop={"2%"}*/}
          {/*      marginBottom={"1%"}*/}
          {/*      placeholder="收货地址"*/}
          {/*      _light={{*/}
          {/*        placeholderTextColor: 'blueGray.400',*/}
          {/*      }}*/}
          {/*      _dark={{*/}
          {/*        placeholderTextColor: 'blueGray.50',*/}
          {/*      }}*/}
          {/*      onChangeText={text => {*/}
          {/*        _setAddress(text);*/}
          {/*      }}*/}
          {/*  />*/}
          {/*  <FormControl.ErrorMessage>{addressErrMsg}</FormControl.ErrorMessage>*/}
          {/*</FormControl>*/}
        </Center>
        <HStack style={styles.card}>
          <Text style={styles.money}>总价: ¥ {params.price}</Text>
          <Button
            onPress={_makeOrder}
            variant="outline"
            colorScheme="success"
            style={styles.submitButton}>
            提交订单
          </Button>
        </HStack>
      </ScrollView>
    </NativeBaseProvider>
  );
}

const styles = StyleSheet.create({
  titleText: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  submitButton: {
    marginTop: "1%",
    marginBottom: "1%",
    marginLeft: "24%",
  },
  bookImage: {
    justifyContent: 'center',
    width: "80%",
    height: 300,
    margin: 10,
  },
  heading: {
    fontFamily: "Microsoft Yahei",
    fontSize: 24,
    fontWeight: '100'
  },
  card: {
    borderColor: '#1e90ff',
    borderWidth: 0.8,
    borderRadius: 20,
    margin: 10
  },
  description: {
    fontSize: 14,
    marginLeft: "4%",
    marginRight: "4%",
    marginBottom: "4%"
  },
  money: {
    color: "#dc143c",
    fontSize: 18,
    marginLeft: "4%",
    marginTop: "4%"
  },
});
