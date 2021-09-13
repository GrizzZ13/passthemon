import React from "react";
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";
import {HomeHeader} from "./HomeHeader";
import {SearchList} from "./SearchList";


export class HomeSearch extends React.Component{
    render() {
        const {navigation} = this.props;
        return(
            <NativeBaseProvider>
                <HomeHeader navigation={navigation}/>
                <SearchList navigation={navigation}/>
            </NativeBaseProvider>
        )
    }
}
