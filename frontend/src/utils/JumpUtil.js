import React from 'react';
import {Platform} from 'react-native';

let mNavigation; //导航

/**
 * 导航设置
 * @param navigation 导航信息
 */
export function appNavigation(navigation) {
  mNavigation = navigation.navigation ? navigation.navigation : navigation;
}

/**
 * 跳转页面
 * @param pageName 路由名称
 * @param obj 传入参数
 * @param isSinglePage 是否单例
 */
export function goOpenPage(pageName, obj, isSinglePage) {
  if (!mNavigation || !pageName) {
    return;
  }
  if (isSinglePage) {
    //栈内只存在一个实例，同一个页面打开多次，显示的是同一个页面
    mNavigation.navigate(pageName, obj ? obj : {});
  } else {
    //每次都打开一个新页面
    mNavigation.push(pageName, obj ? obj : {});
  }
}

/**
 * 返回页面
 * @param pageName 路由名称
 */
export function goBackPage(pageName) {
  if (!mNavigation) {
    return;
  }
  let {
    state: {routes},
  } = mNavigation;
  if (routes && routes.length > 0) {
    if (pageName) {
      //返回到指定的页面
      mNavigation.navigate(pageName);
    } else {
      //默认返回到前一个页面
      mNavigation.goBack(routes[routes.length - 1].key);
    }
  }
}

/**
 *  是否首页面
 * @returns {*}
 */
export function onMainScreen() {
  if (Platform.OS === 'android') {
    //为了安卓双击返回键退出应用
    return (
      !mNavigation ||
      !mNavigation.state ||
      !mNavigation.state.routes ||
      (mNavigation.state &&
        mNavigation.state.routes &&
        mNavigation.state.routes.length <= 1)
    );
  }
  return false;
}
