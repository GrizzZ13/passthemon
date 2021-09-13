import React from "react";
import {getDialog} from "../../service/NotificationService";
import {AlertDialog, Button} from "native-base";

export class DialogNotification extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            title : null,
            content: null,
            date : null,
            isOpen: false,
        }
    }

    _getDialog = () => {
        const callback = (data) => {
            console.warn("haha"+data);
            if(data===null){
                this.setState({
                    isOpen: false,
                })
            }
            else {
                this.setState({
                    title : data.title,
                    content: data.content,
                    date: data.date,
                    isOpen: true,
                })
            }
        }
        let param = null;
        console.warn("haha222");
        getDialog(param, callback);
    }

    _onClose = ()=> {
        this.setState({
            isOpen: false,
        })
    }

    componentDidMount() {
        console.warn("haha");
        this._getDialog();
    }

    render() {
        return(
            <AlertDialog
                leastDestructiveRef={null}
                isOpen={this.state.isOpen}
                onClose={this._onClose}
                motionPreset={"fade"}
            >
                <AlertDialog.Content>
                    <AlertDialog.Header fontSize="lg" fontWeight="bold">
                        {this.state.title}
                    </AlertDialog.Header>
                    <AlertDialog.Body>
                        {this.state.content}
                    </AlertDialog.Body>
                    <AlertDialog.Footer>
                        <Button
                            ref={null}
                            onPress={this._onClose}
                        >
                            我知道了
                        </Button>
                    </AlertDialog.Footer>
                </AlertDialog.Content>
            </AlertDialog>
        )
    }
}
