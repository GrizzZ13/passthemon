import React, {useState} from 'react';
import {View, Button, Image} from 'react-native';
import {launchCamera, launchImageLibrary} from 'react-native-image-picker';
export function Photo(navigation) {
  const [imgs, setImgs] = useState([]);
  const [takeImgs, settakeImgs] = useState([]);
  const addPhoto = () => {
    launchImageLibrary(
      {
        mediaType: 'photo', // 'photo' or 'video' or 'mixed'
        selectionLimit: 0, // 1为一张，0不限制数量
        includeBase64: true,
      },
      res => {
        setImgs(res.assets);
      },
    );
  };

  const addVideo = () => {
    launchImageLibrary(
      {
        mediaType: 'video',
        selectionLimit: 1,
      },
      res => {
        console.log(res);
      },
    );
  };

  const tackPhoto = () => {
    launchCamera(
      {
        mediaType: 'photo',
        cameraType: 'back',
        includeBase64: true,
      },
      res => {
        settakeImgs(res.assets);
      },
    );
  };
  return (
    <View>
      <Button title="启动图库选择图像" onPress={() => addPhoto()} />
      <Button title="启动图库选择视频" onPress={() => addVideo()} />
      <Button title="启动相机拍摄图片" onPress={() => tackPhoto()} />
      {imgs.map((item, index) => {
        return (
          <View key={index}>
            <Image style={{width: 100, height: 100}} source={{uri: item.uri}} />
          </View>
        );
      })}
      {takeImgs.map((item, index) => {
        return (
          <View key={index}>
            <Image style={{width: 100, height: 100}} source={{uri: item.uri}} />
          </View>
        );
      })}
    </View>
  );
}
