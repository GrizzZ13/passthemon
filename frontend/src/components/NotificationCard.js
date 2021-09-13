import {Image, StyleSheet, TouchableOpacity, View} from "react-native";
import screen from "../common/Screen";
import color from "../common/Color";
import {GoodsNameHeading, Heading2, Paragraph, Tip} from "../common/Text";
import React from "react";
import {Pressable, Text, VStack} from "native-base";
import NavigationService from "../service/NavigationService";
import {white} from "react-native-paper/lib/typescript/styles/colors";

export class NotificationCard extends React.Component{

    render() {
        let {item}=this.props.item;
        return(
            <View
                width={'100%'}
                marginTop={4}
                style={{
                    backgroundColor: '#fefefe',
                }}
            >
                <TouchableOpacity
                    onPress={()=>{NavigationService.navigate("NotificationDetail",{
                        title:item.title,
                        content:item.content,
                        date:item.date,
                    })}}
                >
                    <VStack space={4} paddingLeft={4} paddingY={2}>
                        <Text
                            style={{
                                fontSize:18,
                                color:'#505050'
                            }}
                        >
                            {item.title}
                        </Text>
                        <Text
                            style={{
                                fontSize:14,
                                color:'#808080'
                            }}
                        >
                            {item.date}
                        </Text>
                    </VStack>
                </TouchableOpacity>
            </View>);
    }
}
