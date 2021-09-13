import React from 'react';
import {emitter} from "../utils/emitter";
import {Tooltip, Button} from "antd";

export class Logout extends React.Component{
    componentDidMount() {
     localStorage.removeItem('loginState');
     emitter.emit('changeIsLogin',{type:2});
    }
    render() {
        return(
            <div>
                <Tooltip placement="top" title="Prompt Text">
                    <Button>用户信息过期，请重新登录</Button>
                </Tooltip>
            </div>
        )
    }
}
