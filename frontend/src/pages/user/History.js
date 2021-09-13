import * as React from 'react';
import {HistoryList} from '../../components/HistoryList';
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";

export function HistoryScreen({navigation}) {
    return (
        <NativeBaseProvider>
            <HistoryList navigation={navigation} />
        </NativeBaseProvider>
    );
}
