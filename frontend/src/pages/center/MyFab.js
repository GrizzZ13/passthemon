import React from "react";
import {FAB, Portal, Provider} from "react-native-paper";
import {StyleSheet} from "react-native";
import NavigationService from "../../service/NavigationService";

export default class MyFab extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
        }
    }

    render() {
        const stylesClose = StyleSheet.create({
            bgc: {
                backgroundColor : "#ff6c02"
            }
        })
        const stylesOpen = StyleSheet.create({
            bgc: {
                backgroundColor : "#ffb400"
            }
        })

        return(
            <Portal>
                <FAB.Group
                    open={this.state.open}
                    icon={this.state.open ? 'close' : 'plus'}
                    color={'#dedede'}
                    fabStyle={this.state.open ? stylesOpen.bgc : stylesClose.bgc}
                    actions={[
                        {
                            icon: 'upload',
                            label: '传闲置',
                            small: false,
                            onPress: () => NavigationService.navigate('UpLoadGood'),
                        },
                        {
                            icon: 'human-greeting',
                            label: '求好物',
                            small: false,
                            onPress: () => NavigationService.navigate('UpLoadDemand'),
                        },
                    ]}
                    onStateChange={(open) => {
                        this.setState(open);
                    }}
                />
            </Portal>
        )
    }
}
