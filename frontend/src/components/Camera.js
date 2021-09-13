import {launchCamera} from 'react-native-image-picker';
import {Button, View, Image} from 'react-native';
import React from 'react';


const photoOptions = {
  //底部弹出框选项
  title: '请选择',
  cancelButtonTitle: '取消',
  takePhotoButtonTitle: '拍照',
  chooseFromLibraryButtonTitle: '选择相册',
  quality: 0.75, //拍照质量0-1
  allowsEditing: true,
  noData: false, //拍照时不带日期
  storageOptions: {
    skipBackup: true, //跳过备份
    path: 'images',
  },
  saveToPhotos: true, //（布尔值）仅适用于launchCamera，将捕获的图像/视频文件保存到公共照片
  mediaType: 'photo', //如果是 'video'，详见文档 https://github.com/react-native-image-picker/react-native-image-picker/#note-on-file-storage
  maxWidth: 300,
  maxHeight: 300,
  cameraType: 'front',
};
//创建拍照时的配置对象
export class Camera extends React.Component {
  constructor() {
    super();
    this.state = {
      imgUrl: '',
    };
  }

  cameraAction = () => {
    launchCamera(photoOptions, response => {
      //console.warn('response' + response);
      if (response.didCancel) {
        return;
      }
      this.setState({
        imgURL: response.uri,
      });
    });
  };
  render() {
    return (
      <View style={{alignItems: 'center', paddingTop: 200}}>
        <Image
          source={{uri: this.state.imgURL}}
          style={{width: 200, height: 200, borderRadius: 100}}
        />
        <Button title="拍照" onPress={this.cameraAction} />
      </View>
    );
  }
}
