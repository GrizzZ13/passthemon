import React from 'react';
import {FlatList, NativeBaseProvider} from "native-base";
import {getAllOrdinary} from "../../service/NotificationService";
import {ActivityIndicator, Text} from "react-native";
import {NotificationCard} from "../../components/NotificationCard";
import storage from "../../config/Storage";
import BackHeader from "../../common/BackHeader";
export class Notification extends React.Component{
    constructor() {
        super();
    }
    state={
        OrdinaryData:[],
        isEnd:false,
    }
    componentDidMount() {
        this.onListRefresh();

    }

    onListRefresh=()=>{
        let param={};
        const callback=(data2)=>{
            if(data2.list.length===0){
                this.setState({
                    isEnd:true,
                    OrdinaryData:[],
                });
                storage.save({
                        key:'statusCode',
                        data:{
                            value:data2.value,
                        },
                        expires:null,
                    }
                );
            }else{
            this.setState({
                isEnd:false,
                OrdinaryData:data2.list,
            });
            storage.save({
                key:'statusCode',
                data:{
                   value:data2.value,
                },
                expires:null,
                }
            );
            }
        }
        getAllOrdinary(param,callback);
    }

    footerLoading = ()=>{
        if(this.state.isEnd) return (<ActivityIndicator size="large" color="#0000ff"/>);
        else {
            return (<Text style={{fontSize: 16, color : '#d0d0d0', alignSelf: 'center', marginTop: 15}}>
                暂无更多消息
            </Text>);
        }
    }

    render(){
        return(
          <NativeBaseProvider>
              <BackHeader text={'通知列表'}/>
              <FlatList
                  data={this.state.OrdinaryData}
                  ListFooterComponent={() => this.footerLoading()}
                  numColumns={1}
                  refreshing={false}
                  onRefresh={this.onListRefresh}
                  renderItem={(item, index)=>(
                      <NotificationCard item={item}/>
                  )}
              />
          </NativeBaseProvider>
        );
    }
}
