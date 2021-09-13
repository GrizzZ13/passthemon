import storage from './config/Storage';
import React, {useEffect} from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import {DeviceEventEmitter} from "react-native";
import {Comment} from "./pages/orders/Comment";
import {BottomTab} from './common/BottomTab';
import {HomeDetail} from './pages/home/HomeDetail';
import {ChatDetail} from './pages/chat/Chatdetail';
import {SquareDetail} from './pages/square/SquareDetail';
import {UpLoadGood} from './pages/center/UpLoadGood';
import {UploadDemand} from './pages/center/UploadDemand';
import {LoginTest} from './pages/center/LoginTest';
import {MakeOrder} from './pages/orders/MakeOrder';
import {Signup} from './pages/center/Signup';
import {Homepage} from './pages/center/Homepage';
import {MyOrdersV2} from './pages/orders/MyOrdersV2';
import {HistoryScreen} from './pages/user/History';
import {EditGood} from './pages/center/EditGood';
import {FavoriteScreen} from './pages/user/Favorite';
import {MyFollows} from './pages/user/MyFollows';
import {Logout} from './pages/other/Logout';
import {EditDemand} from './pages/center/EditDemand';
import Mine from './pages/user/Mine';
import Other from './pages/user/Other';
import EditUser from './pages/user/EditUser';
import {MyDemand} from "./pages/user/MyDemand";
import {Error} from "./pages/other/Error";
import {HomeSearch} from "./components/HomeSearch";
import {MyGoods} from "./pages/user/MyGoods";
import {OtherComments} from "./pages/user/OtherComments";
import {Notification} from "./pages/chat/Notification";
import {NotificationDetail} from "./pages/chat/NotificationDetail";
import {Reset} from "./pages/center/Reset";
const RootStack = createStackNavigator();
export const AuthContext = React.createContext();

export function RouteControl() {
    const [isLogin, updateIsLogin] = React.useState(false);
    const [userid, updateUserid] = React.useState(0);
    const [token, updateToken] = React.useState('');

    useEffect(() => {
        storage.load({key: 'loginState'}).then(ret => {
            console.log("login-use effect");
            DeviceEventEmitter.emit("login", ret.userid);
            updateIsLogin(true);
            updateUserid(ret.userid);
            updateToken(ret.token);
        });
    }, []);

    const config = {
        animation: 'timing',
        config: {
            duration: 450,
        },
    };
    if (isLogin) {
        return (
            <RootStack.Navigator mode="modal" headerMode="none">
                <RootStack.Screen name="Main" component={BottomTab} />
                <RootStack.Screen name="HomeInside" component={HomeDetail} />
                <RootStack.Screen name="ChatInside" component={ChatDetail} />
                <RootStack.Screen name="SquareInside" component={SquareDetail} />
                <RootStack.Screen name="UpLoadGood" component={UpLoadGood} />
                <RootStack.Screen name="UpLoadDemand" component={UploadDemand}/>
                <RootStack.Screen name="MakeOrder" component={MakeOrder} />
                <RootStack.Screen name="MyOrders" component={MyOrdersV2} />
                <RootStack.Screen name="Mine" component={Mine}/>
                <RootStack.Screen name="Other" component={Other}/>
                <RootStack.Screen name="MyFollows" component={MyFollows}/>
                <RootStack.Screen name="EditUser" component={EditUser}/>
                <RootStack.Screen name="EditGood" component={EditGood}/>
                <RootStack.Screen name="EditDemand" component={EditDemand} />
                <RootStack.Screen name="HistoryScreen" component={HistoryScreen} />
                <RootStack.Screen name="Error" component={Error} />
                <RootStack.Screen name="FavoriteScreen" component={FavoriteScreen} />
                <RootStack.Screen name="MyDemand" component={MyDemand} />
                <RootStack.Screen name="HomeSearch" component={HomeSearch} />
                <RootStack.Screen name="MyGoods" component={MyGoods} />
                <RootStack.Screen name="Comment" component={Comment} />
                <RootStack.Screen name="OtherComments" component={OtherComments} />
                <RootStack.Screen name="Notification" component={Notification} />
                <RootStack.Screen name="Logout" component={Logout} initialParams={{updateIsLogin}}/>
                <RootStack.Screen name="NotificationDetail" component={NotificationDetail}/>
            </RootStack.Navigator>
        );
    } else {
        return (
            <AuthContext.Provider>
                <RootStack.Navigator mode="modal" headerMode="none">
                    <RootStack.Screen
                        name="homepage"
                        component={Homepage}
                        options={{
                            transitionSpec: {
                                open: config,
                                close: config,
                            },
                        }}
                    />
                    <RootStack.Screen
                        name="login"
                        component={LoginTest}
                        initialParams={{updateIsLogin}}
                        options={{
                            transitionSpec: {
                                open: config,
                                close: config,
                            },
                        }}
                    />
                    <RootStack.Screen
                        name="signup"
                        component={Signup}
                        options={{
                            transitionSpec: {
                                open: config,
                                close: config,
                            },
                        }}
                    />
                    <RootStack.Screen
                        name="reset"
                        component={Reset}
                        options={{
                            transitionSpec: {
                                open: config,
                                close: config,
                            },
                        }}
                    />
                </RootStack.Navigator>
            </AuthContext.Provider>
        );
    }
}
