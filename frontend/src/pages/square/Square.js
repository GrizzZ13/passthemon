import * as React from 'react';
import {Text} from 'react-native';
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";
import {SquareDetail} from "./SquareDetail";
import CommonHead from "../../common/CommonHead";
import MyFab from "../center/MyFab";
import {Provider} from "react-native-paper";

export function SquareScreen({navigation}) {
    const title = () => {
        return <Text style={
            {
                fontSize: 18,
                color: '#555555',
                height: 40,
                textAlign:'center',
                justifyContent: 'center',
                marginTop:15,
            }}>
            求好物
        </Text>
    }
  return (
      <NativeBaseProvider>
          <Provider>
              <CommonHead titleItem={title}/>
              <SquareDetail navigation={navigation} />
              <MyFab/>
          </Provider>
      </NativeBaseProvider>
  );
}
