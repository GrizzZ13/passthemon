import React from "react";
import NavigationService from "../service/NavigationService";
import {Box, Center, Text} from "native-base";
import {Image, TouchableOpacity} from "react-native";
import CommonHead from "./CommonHead";

export default class BackHeader extends React.Component{
    constructor(props) {
        super(props);
    }

    renderLeft() {
        return(
            <Box ml={2}>
                <TouchableOpacity
                    style={{
                        flexDirection: 'row',
                        justifyContent: 'center',
                        alignItems: 'center',
                        width: 70,
                        height: 30,
                        borderColor: '#e6e6e6',
                        borderWidth: 1,
                        borderRadius: 20,
                    }}
                    activeOpacity={0.7}
                    onPress={NavigationService.goBack
                    }>
                    <Image
                        source={require('../asset/img/left_arrow.png')}
                        style={{
                            width: 15,
                            height: 15,
                            marginRight: 5,
                        }}
                        resizeMode={'contain'}
                        alt={' '}
                    />
                    <Text style={{
                        fontSize: 14,
                        color: '#515151',
                    }}>返回</Text>
                </TouchableOpacity>
            </Box>
        )
    }

    renderTitle() {
        return (
            <Center paddingRight={16}>
                <Text style={{
                    fontSize: 18,
                    height: 40,
                    textAlign:'center',
                    justifyContent: 'center',
                    paddingTop: "2.4%",
                }}>
                    {this.props.text}
                </Text>
            </Center>
        );
    }

    render() {
        return(
            <CommonHead
                leftItem={() => this.renderLeft()}
                titleItem={() => this.renderTitle()}
                navBarColor={this.props.backgroundColor}
            />
        )
    }
}
