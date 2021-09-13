import React from 'react';
export class RabbitmqWatch extends React.Component{
    constructor() {
        super();
    }
    render(){
        return(
            <div>
                <div>
                <span><a href="http://123.60.78.242:4002" target="_blank" rel="noopener noreferrer">监控002服务器的消息队列</a></span>
                </div>
                <div>
                <span><a href="http://123.60.78.242:4003" target="_blank" rel="noopener noreferrer">监控003服务器的消息队列</a></span>
                </div>
            </div>
        )
    }
}
