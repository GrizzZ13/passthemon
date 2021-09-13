import {
  Pressable,
  Image,
  Box,
  Fab,
  IconButton,
  Button,
  NativeBaseProvider,
} from 'native-base';
import Icon from 'react-native-vector-icons/FontAwesome';
import React from 'react';
export class ImageWrapBase64 extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <NativeBaseProvider>
        <Box>
          <Pressable
            onPress={() => {
              this.props.removeImage(this.props.index);
            }}>
            <Icon size={20} name="close" backgroundColor="#3b5998" />
          </Pressable>
          <Image
            style={{width: 100, height: 100}}
            source={{uri: 'data:image/jpg;base64,' + this.props.base64}}
          />
        </Box>
      </NativeBaseProvider>
    );
  }
}
