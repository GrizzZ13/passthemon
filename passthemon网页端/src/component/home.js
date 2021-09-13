import React from 'react';
import {Layout, Menu, Breadcrumb, Button, Tooltip, Alert} from 'antd';
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons';
import {UploadMessage} from './uploadMessage';
import {RabbitmqWatch} from "./rabbitmqWatch";
import {SpringAdmin} from "./SpringAdmin";
import {history} from "../utils/history";
import {getUserInfoByUserId} from "../service/userService";
import Text from "antd/es/typography/Text";
const { SubMenu } = Menu;
const { Header, Content, Footer, Sider } = Layout;
export class Home extends React.Component{
    constructor(props) {
        super(props);
    }
    state={
      image:'',
      name:'',
      userid:0,
      key:1,
    }
    componentDidMount() {
        let ret=JSON.parse(localStorage.getItem('loginState'));
        console.log(ret);
        this.setState({
            userid:ret.userid,
        });
        const params={
            userId:ret.userid
        };
        console.log(params);
        const callback=(data)=>{
            this.setState({
                image:data.image,
                name:data.name,
            })
        }
        getUserInfoByUserId(params,callback);
    }

    renderImage=()=>{

       if(this.state.image=='') return ;
       else {
           let str='data:image/png;base64,'+this.state.image;
           console.log(str);
           return <img src={str} alt=''/>
       }
    }
    renderCenter=()=>{
        if(this.state.key=='1') return (<UploadMessage/>);
        else if(this.state.key=='5') return (<SpringAdmin/>);
        else if(this.state.key=='6') return (<RabbitmqWatch/>);
        else {
                    return ( <Tooltip placement="top" title="Prompt Text">
                        <Alert
                            message="Success Tips"
                            type="success"
                            description="暂无页面"
                            showIcon
                        />
                    </Tooltip>);
        }
    }
    setkey=(e)=>{
        if(this.state.key==e.key) return ;
        else {
            this.setState({
                key:e.key,
            })
        }
    }
    render() {
        let height=document.body.clientHeight;
        return(
            <Layout>
                <Header className="header">
                    <div className="logo" />
                </Header>
                <Content style={{ padding: '0 50px' }}>
                    {'你好 '+this.state.name+' '}
                    <Button onClick={()=>{history.push('/Logout')}}>
                        退出登录
                    </Button>
                    <Layout className="site-layout-background" style={{ padding: '24px 0' }}>
                        <Sider className="site-layout-background" width={200}>
                            <Menu
                                mode="inline"
                                defaultSelectedKeys={['1']}
                                defaultOpenKeys={['sub1']}
                                style={{ height: '100%' }}
                            >
                                <SubMenu key="sub1" icon={<UserOutlined />} title="消息管理">
                                    <Menu.Item key="1" onClick={this.setkey}>发布信息</Menu.Item>
                                    <Menu.Item key="2" onClick={this.setkey}>option2</Menu.Item>
                                    <Menu.Item key="3" onClick={this.setkey}>option3</Menu.Item>
                                    <Menu.Item key="4" onClick={this.setkey}>option4</Menu.Item>
                                </SubMenu>
                                <SubMenu key="sub2" icon={<LaptopOutlined />} title="监控">
                                    <Menu.Item key="5" onClick={this.setkey}>SpringBoot监控</Menu.Item>
                                    <Menu.Item key="6" onClick={this.setkey}>rabbitmq监控</Menu.Item>
                                    <Menu.Item key="7" onClick={this.setkey}>option7</Menu.Item>
                                    <Menu.Item key="8" onClick={this.setkey}>option8</Menu.Item>
                                </SubMenu>
                                <SubMenu key="sub3" icon={<NotificationOutlined />} title="subnav 3">
                                    <Menu.Item key="9" onClick={this.setkey}>option9</Menu.Item>
                                    <Menu.Item key="10" onClick={this.setkey}>option10</Menu.Item>
                                    <Menu.Item key="11" onClick={this.setkey}>option11</Menu.Item>
                                    <Menu.Item key="12" onClick={this.setkey}>option12</Menu.Item>
                                </SubMenu>
                            </Menu>
                        </Sider>
                        <Content style={{ padding: '0 24px', minHeight: 280 }}>
                            {this.renderCenter()}
                        </Content>
                    </Layout>
                </Content>
                <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
            </Layout>
        )
    }
}
