import React from "react";
import {NativeBaseProvider} from "native-base/src/core/NativeBaseProvider";
import {HomeGoodsList} from "../../components/HomeGoodsList";
import BackHeader from "../../common/BackHeader";


export class MyGoods extends React.Component {
    render() {
        return(
            <NativeBaseProvider>
                <BackHeader text={'已发布'}/>
                <HomeGoodsList fromView = {"Mine"} navigation={this.props.navigation} userid={this.props.route.params.userid}/>
            </NativeBaseProvider>
        );
    }
}
