import React from 'react';
import 'antd/dist/antd.css';
import { Form, Input, Button, Checkbox,Card ,Space} from 'antd';
import {emitter} from "../utils/emitter";
import {Login} from "../service/userService";
import * as userService from "../service/userService";
import MD5 from 'js-md5';
const layout = {

    labelCol: {
        span: 8,
    },
    wrapperCol: {
        span: 8,
    },
};
const tailLayout = {
    wrapperCol: {
        offset: 8,
        span: 16,
    },
};

export class LoginScreen extends React.Component{
    constructor(props) {
        super(props);
    }
    onFinish = (values) => {
        console.log('Success:', values);
        let data={
            email:values.email,
            password: values.password,
        };
    const callback=(msg)=>{
        console.log(msg);
         if(msg.status > 0){
             let Info={
                 userid:msg.data.userid,
                 token:msg.data.token,
             };
         localStorage.setItem("loginState",JSON.stringify(Info));
         let param={
             type:1,
         }
         emitter.emit("changeIsLogin",param);
         }else {
             alert('用户名或密码错误');
         }
    };
    Login(data,callback);
    };

    onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };
    render() {
        return (
            <div>
                <Form
                    {...layout}
                    name="basic"
                    justify="center"

                    initialValues={{
                        remember: true,
                    }}
                    onFinish={this.onFinish}
                    onFinishFailed={this.onFinishFailed}
                >
                    <Form.Item
                        label="Email"
                        name="email"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your email!',
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>

                    <Form.Item
                        label="Password"
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your password!',
                            },
                        ]}
                    >
                        <Input.Password/>
                    </Form.Item>

                    <Form.Item {...tailLayout} name="remember" valuePropName="checked">
                        <Checkbox>Remember me</Checkbox>
                    </Form.Item>

                    <Form.Item {...tailLayout}>
                        <Button type="primary" htmlType="submit">
                            登录
                        </Button>
                        <Space/>
                        <Button type="primary" >
                            <a href="/register">注册</a>
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        )
    }
};
