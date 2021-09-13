import * as React from 'react';
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";
import {FavoriteList} from "../../components/FavoriteList";


export function FavoriteScreen({navigation}) {
    return (
        <NativeBaseProvider>
            <FavoriteList navigation={navigation} />
        </NativeBaseProvider>
    );
}
