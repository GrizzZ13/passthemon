import React from 'react';
import {Button, DatePicker, Space,Tag,Radio,Alert} from 'antd';
import { Input } from 'antd';
import {publish} from "../service/notificationService";
const { TextArea } = Input;
const { RangePicker } = DatePicker;


export class UploadMessage extends React.Component{
   constructor(props) {
       super(props);
   }
   state={
       time:[],
       text:'',
       type:1,
       disabled:false,
       buttonText:'发布',
       title:'',
   }
    onChangeDate=(value, dateString)=> {
        console.log('Selected Time: ', value);
        console.log('Formatted Selected Time: ', dateString);
        this.setState({
            time:dateString,
        });
    }
    onChangeText=(e)=>{
       console.log(e.target.value);
       this.setState({
          text:e.target.value,
       });
    }
    onChangeTitle=(e)=>{
       this.setState({
           title:e.target.value,
       })
    }
    onChangeType=(e)=>{
       this.setState({
           type:e.target.value,
       })
    }
    onOk=(value,dateString)=> {
        console.log('onOk: ', value);
    }
    submit=()=>{
       let user=JSON.parse(localStorage.getItem('loginState'));
       this.setState({
           disabled:true,
           buttonText:'正在发布',
       });
       const callback=(data)=>{
           alert('发布成功');
           this.setState({
               disabled:false,
               buttonText:'发布',
           })
       };
       let params={
           title: this.state.title,
           content:this.state.text,
           type:this.state.type,
           date:this.state.time[0],
           expired:this.state.time[1],
       };
        publish(params,callback);
    }
   render() {
       return(
           <Space direction="vertical" style={{width:'70%'}}>
               <TextArea
                   value={this.state.title}
                   onChange={this.onChangeTitle}
                   placeholder="输入标题"
                   autoSize={{ minRows: 1, maxRows: 2 }}
               />
               <TextArea
                   value={this.state.text}
                   onChange={this.onChangeText}
                   placeholder="输入发布内容"
                   autoSize={{ minRows: 3, maxRows: 5 }}
               />
               <Tag color="#f50">输入发布内容的时间范围</Tag>
               <RangePicker
                   showTime={{ format: 'HH:mm' }}
                   format="YYYY-MM-DD HH:mm"
                   onChange={this.onChangeDate}
                   onOk={this.onOk}
               />
               <Tag color="#f50">输入发布类型</Tag>
               <Radio.Group onChange={this.onChangeType} value={this.state.type}>
                   <Space direction="vertical">
                       <Radio value={1}>公告</Radio>
                       <Radio value={2}>通知</Radio>
                   </Space>
               </Radio.Group>
               <Button disabled={this.state.disabled} onClick={this.submit}>
                   {this.state.buttonText}
               </Button>
           </Space>

       )
   }
}
