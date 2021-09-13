//用法：<UserListItem username={} image={} onPress={}/>
//修改空间大
//
import React from 'react';
import {Box, Image, Text, Pressable, View} from 'native-base';
import Icon from 'react-native-vector-icons/FontAwesome';
import NavigationService from "../../service/NavigationService";

export class ChatListItem extends React.Component {
    renderImage = () => {
        if (this.props.image === null || this.props.image === undefined) {
            return (
                <Box w={'20%'}>
                    <Pressable onPress={()=>{
                        NavigationService.navigate('Other',{userid: this.props.id});
                    }
                    } mr={'5'}>
                        <Image
                            size={50}
                            borderRadius={25}
                            marginLeft={15}
                            source={require('./User.png')}
                            alt={' '}
                        />
                    </Pressable>
                </Box>
            );
        } else {
            return (
                <Box w={'20%'}>
                    <Pressable onPress={()=>{
                        NavigationService.navigate('Other',{userid: this.props.id});
                    }
                    } mr={'5'}>
                        <Image
                            marginLeft={15}
                            size={50}
                            borderRadius={25}
                            source={{uri: 'data:image/jpg;base64,' + this.props.image}}
                            alt={' '}
                        />
                    </Pressable>
                </Box>
            );
        }
    };
    renderTitle = () => {
        if (this.props.username === null || this.props.username === undefined) {
            return (
                <Box w={'60%'}>
                    <Pressable  onPress={this.props.onPress} alignItems={'center'}>
                        <Text fontSize="lg" isTruncated color={'#555555'}>
                            unnamed
                        </Text>
                    </Pressable>
                </Box>
            );
        } else {
            return (
                <Box w={'60%'}>
                    <Pressable onPress={this.props.onPress} alignItems={'center'}>
                        <Text fontSize="lg" isTruncated color={'#555555'}>
                            {this.props.username}
                        </Text>
                    </Pressable>
                </Box>
            );
        }
    };

    renderRight = () => {
        return (
            <Box w={'20%'} alignItems={'center'}>
                <Pressable onPress={this.props.onPress}>
                    <Icon size={36} name="comment" color={'#d0d0d0'}/>
                </Pressable>
            </Box>
        );
    };

    render() {
        return (
            <View
                height={70}
                style={{
                    flexDirection: 'row', //横向排
                    justifyContent: 'space-between', //主轴对齐方式
                    alignItems: 'center', //次轴对齐方式（上下居中）
                    }
                }
            >
                {this.renderImage()}
                {this.renderTitle()}
                {this.renderRight()}
            </View>
        );
    }
}
