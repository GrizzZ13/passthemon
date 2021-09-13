/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import NavigationService from './src/service/NavigationService';
import {wsurl} from "./src/config/url";
import {RouteControl} from './src/RouteControl';
import {DeviceEventEmitter,} from 'react-native';
import ReconnectingWebSocket from "react-native-reconnecting-websocket";
import * as NetInfo from "@react-native-community/netinfo";
import {CHANNEL, USER} from "./src/config/ChatConstant";

class App extends React.Component{

    static ws;

    UNSAFE_componentWillMount() {
        this.testListener = DeviceEventEmitter.addListener("hahaha", () => {
            console.warn("test");
        })
        this.loginListener = DeviceEventEmitter.addListener("login", userid => {
            console.log("DeviceEventEmitter-listen");
            console.log("login userid - " + userid);

            if(App.ws!==null && typeof App.ws=="object" && typeof App.ws.close=="function") {
                console.log("close");
                App.ws.close();
                App.ws=null;
            }

            console.log('connect to websocket server');
            App.ws = new ReconnectingWebSocket(wsurl + "/" + userid.toString())

            App.ws.onmessage = (msg) => {
                let data = JSON.parse(msg.data);
                if(data.type === USER)
                    DeviceEventEmitter.emit("onmessage", data);
                else if(data.type === CHANNEL) {
                    console.log("app.js - ws emit channel");
                    DeviceEventEmitter.emit("channel", data);
                }
            }

            // Subscribe
            this.unsubscribe = NetInfo.addEventListener(state => {
                console.log("Connection type", state.type);
                console.log("Is connected?", state.isConnected);
                if(state.type === 'none' ||  state.type === 'unknown') {
                    console.log("NetInfoListener - close")
                    App.ws.close();
                }else {
                    console.log("NetInfoListener - reconnect")
                    App.ws.reconnect();
                }
            });
        })

        this.logoutListener = DeviceEventEmitter.addListener("logout", ()=>{
            if(typeof App.ws=="object" && typeof App.ws.close=="function") {
                console.log('App.js logout listener reset App.ws');
                App.ws.close();
                App.ws = null;
            }
            if(typeof this.unsubscribe==="function") this.unsubscribe();
        })
        console.log("App will mount");
    }

    componentWillUnmount() {
        this.loginListener.remove();
        this.logoutListener.remove();
        if(typeof this.unsubscribe==="function") this.unsubscribe();
        if(App.ws != null) App.ws.close();
        console.log("App will unmount");
    }

    render() {
        return (
            <NavigationContainer
                ref={navigatorRef => {
                    NavigationService.setTopLevelNavigator(navigatorRef);
                }}>
                <RouteControl />
            </NavigationContainer>
        );
    }
}

export default App;
