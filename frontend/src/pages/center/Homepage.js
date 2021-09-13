import React from 'react';
import {
  Button,
  NativeBaseProvider,
  VStack,
  Center,
  Image, HStack,
} from 'native-base';

export function Homepage({route, navigation}) {
  return (
    <NativeBaseProvider>
      <VStack marginTop={10}>
        <Center height={160}>
          <Button
              backgroundColor={'#f68946'}
              onPress={() => navigation.navigate('signup')}
              width={160}
              height={160}
              rounded={80}
              _text={{fontSize:'24px'}}
          >
            注册
          </Button>
        </Center>
        <Center height={280}>
          <Image
              rounded={110}
              source={require('./icon.jpg')}
              alt="Alternate Text"
              size={220}
          />
        </Center>
        <Center height={160}>
          <Button
              backgroundColor={'#f6a246'}
              onPress={() => navigation.navigate('login')}
              width={160}
              height={160}
              rounded={80}
              _text={{fontSize:'24px'}}
          >
            登录
          </Button>
        </Center>
      </VStack>
    </NativeBaseProvider>
  );
}
