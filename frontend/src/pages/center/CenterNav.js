import React from 'react';
import {Button, Center, NativeBaseProvider, View, VStack} from 'native-base';
export function CenterNav({navigation}) {
  return (
    <NativeBaseProvider>
      <VStack marginTop={40}>
        <Center height={56}>
          <Button
              backgroundColor={'#e75550'}
              onPress={() => navigation.navigate('UpLoadGood')}
              width={32}
              height={32}
              rounded={64}>
            传闲置
          </Button>
        </Center>
        <Center height={56}>
          <Button
              backgroundColor={'#ed7a3b'}
              onPress={() => navigation.navigate('UpLoadDemand')}
              width={32}
              height={32}
              rounded={64}>
            我想要
          </Button>
        </Center>
      </VStack>
    </NativeBaseProvider>
  );
}
