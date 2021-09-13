import React from 'react';
import {Tooltip, Button,Alert} from "antd";
export class Error extends React.Component{
    constructor(props) {
        super(props);
    };
    render() {
        return(
            <div>
                <Tooltip placement="top" title="Prompt Text">
                    <Alert
                        message="Error"
                        description="权限不足"
                        type="error"
                        showIcon
                    />
                </Tooltip>
            </div>
        );
    }
}
