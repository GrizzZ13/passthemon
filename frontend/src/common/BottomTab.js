import * as React from 'react';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {ChatScreen} from '../pages/chat/Chat';
import {HomeScreen} from '../pages/home/Home';
import {SquareScreen} from '../pages/square/Square';
import Mine from '../pages/user/Mine';
import {NativeBaseProvider, Image} from 'native-base';

const Tab = createBottomTabNavigator();

export function BottomTab() {
  return (
    <NativeBaseProvider>
      <Tab.Navigator
        initialRouteName={'Home'}
        tabBarOptions={{
          marginBottom: 10,
          activeTintColor: '#ff6c02',
          inactiveTintColor: '#666666',
        }}>
        <Tab.Screen
          name="Home"
          component={HomeScreen}
          options={{
            tabBarLabel: 'Home',
            tabBarIcon: ({focused, color, size}) => {
              return focused ? (
                <Image
                  alt={'Home'}
                  source={require('./Home_Active.png')}
                  size={10}
                />
              ) : (
                <Image alt={'Home'} source={require('./Home.png')} size={8} />
              );
            },
          }}
        />
          <Tab.Screen
              name="Demand"
              component={SquareScreen}
              options={{
                  tabBarIcon: ({focused, color, size}) => {
                      return focused ? (
                          <Image
                              alt={'Square'}
                              source={require('./Square_Active.png')}
                              size={10}
                          />
                      ) : (
                          <Image
                              alt={'Square'}
                              source={require('./Square.png')}
                              size={8}
                          />
                      );
                  },
              }}
          />
        <Tab.Screen
          name="Chat"
          component={ChatScreen}
          options={{
            tabBarIcon: ({focused, color, size}) => {
              return focused ? (
                <Image
                  alt={'Chat'}
                  source={require('./Msg_Active.png')}
                  size={10}
                />
              ) : (
                <Image alt={'Chat'} source={require('./Msg.png')} size={8} />
              );
            },
          }}
        />
        <Tab.Screen
          name="Profile"
          component={Mine}
          options={{
            tabBarIcon: ({focused, color, size}) => {
              return focused ? (
                <Image
                  alt={'Profile'}
                  source={require('./User_Active.png')}
                  size={10}
                />
              ) : (
                <Image
                  alt={'Profile'}
                  source={require('./User.png')}
                  size={8}
                />
              );
            },
          }}
        />
      </Tab.Navigator>
    </NativeBaseProvider>
  );
}
