import {Image} from "native-base";
import * as React from "react";

export default function UserIcon(data) {
    let source = data.source;
    let size = data.size;
    if(source === undefined || source === null || source === "NULL"){
        return <Image
            size={size}
            borderRadius={size/2}
            alt="UserIcon"
            source={require('./User.png')}
        />;
    }
    else{
        return <Image
            size={size}
            borderRadius={size/2}
            alt="goodsImage"
            source={{uri: 'data:image/jpg;base64,' + source}}
        />;
    }
}
