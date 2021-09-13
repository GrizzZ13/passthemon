import * as React from 'react';
import {NativeBaseProvider} from 'native-base/src/core/NativeBaseProvider';
import {HomeHeader} from "../../components/HomeHeader";
import MyFab from "../center/MyFab";
import {HomeGoodsList} from "../../components/HomeGoodsList";
import {Provider} from "react-native-paper";
import {DialogNotification} from "./DialogNotification";



export function HomeScreen({navigation}) {
    return (
        <NativeBaseProvider>
            <Provider>
                <DialogNotification/>
                <HomeHeader navigation={navigation}/>
                <HomeGoodsList navigation={navigation}/>
                <MyFab/>
            </Provider>
        </NativeBaseProvider>
    );
}
