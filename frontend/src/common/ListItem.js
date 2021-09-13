import React from 'react';
import {Image, StyleSheet, Text, TouchableOpacity, View} from 'react-native';

export class ListItem extends React.PureComponent {
  static defaultProps = {
    leftText: '',
    rightText: '',
    isShowUnderline: true,
    isShowArrow: true,
  };

  _renderRight = () => {
    if (!this.props.rightText && !this.props.rightComponent) {
      return <Text />;
    }

    if (this.props.rightText) {
      return <Text style={styles.itemRightText}>{this.props.rightText}</Text>;
    }

    if (this.props.rightComponent) {
      return <this.props.rightComponent />;
    }
  };

  render() {
    return (
      <TouchableOpacity
        activeOpacity={0.9}
        style={[
          styles.itemContainer,
          this.props.isShowUnderline && styles.itemBorderBottom,
        ]}
        onPress={this.props.onPress}>
        <Text style={styles.itemLeftText}>{this.props.leftText}</Text>

        <View style={styles.itemRightContainer}>
          {this._renderRight()}
          {!this.props.rightComponent && this.props.isShowArrow && (
            <Image
              style={styles.itemRightImg}
              source={require('./i_right.png')}
            />
          )}
        </View>
      </TouchableOpacity>
    );
  }
}

export const styles = StyleSheet.create({
  itemContainer: {
    flexDirection: 'row',
    backgroundColor: '#f8f8f8',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: 50,
  },
  itemBorderBottom: {
    borderBottomWidth: 1,
    borderBottomColor: '#e6e6e6',
  },
  itemLeftText: {
    fontSize: 14,
    color: '#000',
  },
  itemRightContainer: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  itemRightText: {
    color: '#bfbfbf',
    fontSize: 12,
  },
  itemRightImg: {
    width: 20,
    height: 20,
    marginHorizontal: 7,
  },
  container: {
    flex: 1,
    backgroundColor: '#F8F8F8',
  },
  headContainer: {
    paddingHorizontal: 15,
    paddingBottom: 10,
    paddingTop: 30,
  },
  headTopContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  topBtnStyle: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    width: 70,
    height: 30,
    borderColor: '#e6e6e6',
    borderWidth: 1,
    borderRadius: 20,
  },
  headTopImg: {
    width: 15,
    height: 15,
    marginRight: 5,
  },
  headTopText: {
    fontSize: 12,
    color: '#515151',
  },
  headCenterContainer: {
    alignItems: 'center',
    marginBottom: 15,
  },
  userImg: {
    width: 100,
    height: 100,
    borderRadius: 100, //调圆头像用
  },
  userNickname: {
    marginVertical: 5,
    fontSize: 20,
    color: '#000',
  },
  positionContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  positionImg: {
    width: 10,
    height: 10,
    marginRight: 2,
  },
  positionText: {
    color: '#bfbfbf',
    fontSize: 10,
  },
  headBottomContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  bottomBtn: {
    alignItems: 'center',
  },
  bottomNum: {
    fontSize: 20,
    color: '#000',
  },
  bottomText: {
    color: '#bfbfbf',
    fontSize: 12,
  },
  transitionView: {
    height: 5,
    backgroundColor: 'rgba(230,230,230, .5)',
  },
  settingListContainer: {
    paddingLeft: 20,
  },
});
