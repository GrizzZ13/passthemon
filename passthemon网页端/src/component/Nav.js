import React, { Component } from 'react';
import { Menu } from 'antd';
const { SubMenu } = Menu;
export class Nav extends Component {
    constructor(props) {
        super(props);
        //react定义数据
        this.state = {

        }
    }
    handleClick = e => {
        console.log('click ', e);
    };
    render() {
        return (
            <Menu
                onClick={this.handleClick}
                style={{ width: 256 }}
                defaultSelectedKeys={['1']}
                defaultOpenKeys={['sub1']}
                mode="inline"
            >
                <SubMenu
                    key="sub1"
                    title={
                        <span>
                  {/*<Icon type="mail" />*/}
                  <span>数据管理</span>
                </span>
                    }
                >
                    <Menu.Item key="3">用户管理</Menu.Item>
                    <Menu.Item key="4">角色管理</Menu.Item>
                </SubMenu>
                <SubMenu
                    key="sub2"
                    title={
                        <span>
                  {/*<Icon type="appstore" />*/}
                  <span>配置管理</span>
                </span>
                    }
                >
                    <Menu.Item key="5">参数配置</Menu.Item>
                    <Menu.Item key="6">地图配置</Menu.Item>

                </SubMenu>
                <SubMenu
                    key="sub4"
                    title={
                        <span>
                  {/*<Icon type="setting" />*/}
                  <span>签到管理</span>
                </span>
                    }
                >
                    <Menu.Item key="9">签到查询</Menu.Item>
                    <Menu.Item key="10">签到统计</Menu.Item>

                </SubMenu>
            </Menu>
        );
    }
}

