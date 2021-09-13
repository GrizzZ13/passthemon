import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {StyleSheet, View, Dimensions} from 'react-native';

// 取得屏幕的宽高Dimensions
const {width, height} = Dimensions.get('window');

export default class CommonHead extends Component {
  static propTypes = {
    leftItem: PropTypes.func,
    titleItem: PropTypes.func,
    rightItem: PropTypes.func,
  };
  renderLeftItem() {
    if (this.props.leftItem === undefined) {
      return;
    }
    return this.props.leftItem();
  }
  renderTitleItem() {
    if (this.props.titleItem === undefined) {
      return;
    }
    return this.props.titleItem();
  }
  renderRightItem() {
    if (this.props.rightItem === undefined) {
      return;
    }
    return this.props.rightItem();
  }
  render() {
    return (
      <View
        style={[
          {
            width: width,
            height: 50,
            backgroundColor: this.props.navBarColor || '#fff', //背景色，默认白色
            flexDirection: 'row', //横向排
            justifyContent: 'space-between', //主轴对齐方式
            alignItems: 'center', //次轴对齐方式（上下居中）
            borderBottomWidth: this.props.borderBottomWidth || 0, //是否有下边框
            borderColor: this.props.borderColor || '#ccc',
          },
          this.props.navBarStyle,
        ]}>
        <View>{this.renderLeftItem()}</View>
        <View>{this.renderTitleItem()}</View>
        <View>{this.renderRightItem()}</View>
      </View>
    );
  }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//用法，传入三个组件来构成头部的左中右三部分组件,如下
// <CommonHead
//                     leftItem={() => this.renderLeftItem()}
//                     titleItem={() => this.renderTitleItem()}
//                     rightItem={() => this.renderRightItem()}
//                 />
//参考地址 https://github.com/gingerJY/example/tree/master/RN_commonHead
//       https://www.cnblogs.com/MaiJiangDou/p/8329984.html
