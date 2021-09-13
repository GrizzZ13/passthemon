import React, {PureComponent} from 'react';
import {
    View,
    Text,
    StyleSheet,
    TouchableOpacity,
    Image,
} from 'react-native';
import {GoodsNameHeading, Heading2, Paragraph, Tip} from '../common/Text';
import screen from '../common/Screen';
import color from '../common/Color';
import NavigationService from "../service/NavigationService";

export default class GroupPurchaseCell extends PureComponent {
    renderBuyerRight=()=>{
        let {item}=this.props.item;
        if(item.comment==='NoComment'){
            return (
                <View style={styles.buttonContainer}>
                    <TouchableOpacity
                        style={styles.buttonItem}
                        onPress={()=>{NavigationService.navigate('Comment',{
                            opt:1,
                            orderId:item.id,
                            goodsName: item.goodsName,
                            comment:item.comment,
                            rating:item.rating,
                        })}}>
                        <Tip>{'评价订单'}</Tip>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={styles.buttonItem}
                        onPress={()=>{NavigationService.navigate('Other',{userid:item.seller})}}>
                        <Tip>{'联系卖家'}</Tip>
                    </TouchableOpacity>
                </View>
            );
        }
        else {
            return(
                <View style={styles.buttonContainer}>
                    <TouchableOpacity
                        style={styles.buttonItem}
                        onPress={()=>{NavigationService.navigate('Comment',{
                            opt:2,
                            orderId:item.id,
                            goodsName: item.goodsName,
                            comment:item.comment,
                            rating:item.rating,
                        })}}>
                        <Tip>{'查看评价'}</Tip>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={styles.buttonItem}
                        onPress={()=>{NavigationService.navigate('Other',{userid:item.seller})}}>
                        <Tip>{'联系卖家'}</Tip>
                    </TouchableOpacity>
                </View>
            );
        }
    }
    renderSellerRight=()=>{
        let {item}=this.props.item;
        if(item.comment==='NoComment'){
            return (
                <View style={styles.buttonContainer}>
                <TouchableOpacity  style={styles.buttonItem}>
                    <Tip>{'买家未评价'}</Tip>
                </TouchableOpacity>
                    <TouchableOpacity
                        onPress={()=>{NavigationService.navigate('Other',{userid:item.buyer})}}
                        style={styles.buttonItem}>
                        <Tip>{'联系买家'}</Tip>
                    </TouchableOpacity>
                </View>    );

        }
        else {
            return(
                <View style={styles.buttonContainer}>
                <TouchableOpacity
                    style={styles.buttonItem}
                    onPress={()=>{NavigationService.navigate('Comment',{
                    opt:3,
                    orderId:item.id,
                    goodsName: item.goodsName,
                    comment:item.comment,
                    rating:item.rating,
                })}}>
                    <Tip>{'查看评价'}</Tip>
                </TouchableOpacity>
                    <TouchableOpacity
                        onPress={()=>{NavigationService.navigate('Other',{userid:item.buyer})}}
                        style={styles.buttonItem}
                    >
                        <Tip>{'联系买家'}</Tip>
                    </TouchableOpacity>
                </View>
            );
        }
    }
    renderOther=()=>{
        let {item}=this.props.item;
        if(item.comment==='NoComment'){
            return (
                <View style={{flex:1, justifyContent:'flex-end'}}>
                    <Heading2 style={styles.price}>买家未评价</Heading2>
                </View>
            );

        }
        else {
            return(
                <View style={{flex:1, justifyContent:'flex-end'}}>
                    <TouchableOpacity
                        style={styles.buttonItem}
                        onPress={()=>{NavigationService.navigate('Comment',{
                            opt:3,
                            orderId:item.id,
                            goodsName: item.goodsName,
                            comment:item.comment,
                            rating:item.rating,
                        })}}>
                        <Heading2 style={styles.price}>查看评价</Heading2>
                    </TouchableOpacity>
                </View>
            );
        }
    }
    render(){
        let {item}=this.props.item;
        for(let i = 0; i < this.props.imagesList.length; i++){
            if(item.goods === this.props.imagesList[i].goodsId){
                item.image = this.props.imagesList[i].image;
                break;
            }
        }
        if(this.props.opt===1){
        return (
            <View style={styles.container}>
                {
                    item.image === null ?
                        <Image source={require('../asset/img/preGoodsImage.png')} style={styles.icon}/>
                        :
                        <Image source={{uri: 'data:image/jpg;base64,' + item.image}} style={styles.icon} />
                }

                <View style={styles.centerContainer}>
                    <GoodsNameHeading>{item.goodsName}</GoodsNameHeading>

                    <View style={{flex:1, justifyContent:'flex-end'}}>
                        <Heading2 style={styles.price}>{item.saleroom+"元  数量:"+item.num}</Heading2>
                    </View>
                    <Paragraph numberOfLine={0} style={{marginTop: 8}}>{item.timestamp}</Paragraph>
                </View>
                <View style={styles.rightContainer}>
                    {this.renderSellerRight()}
                </View>

            </View>
        );}
        else if(this.props.opt===0){
            return (
                <View style={styles.container}>
                    {
                        item.image === null ?
                            <Image source={require('../asset/img/preGoodsImage.png')} style={styles.icon}/>
                            :
                            <Image source={{uri: 'data:image/jpg;base64,' + item.image}} style={styles.icon} />
                    }
                    <View style={styles.centerContainer}>
                        <GoodsNameHeading>{item.goodsName}</GoodsNameHeading>
                        <View style={{flex:1, justifyContent:'flex-end'}}>
                            <Heading2 style={styles.price}>{item.saleroom+"元  数量:"+item.num}</Heading2>
                        </View>
                        <Paragraph numberOfLine={0} style={{marginTop: 8}}>{item.timestamp}</Paragraph>
                    </View>
                    <View style={styles.rightContainer}>
                        {this.renderBuyerRight()}
                    </View>
                </View>
            );
        }
        else if(this.props.opt===2){
            return (
                <View style={styles.container}>
                    {
                        item.image === null ?
                            <Image source={require('../asset/img/preGoodsImage.png')} style={styles.icon}/>
                            :
                            <Image source={{uri: 'data:image/jpg;base64,' + item.image}} style={styles.icon} />
                    }
                    <View style={styles.centerContainer}>
                        <GoodsNameHeading>{item.goodsName}</GoodsNameHeading>
                        {this.renderOther()}
                    </View>
                </View>
            );
        }
    }
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        height: 140,
        borderBottomWidth: screen.onePixel,
        borderColor: color.border,
        backgroundColor: 'white',
    },
    buttonContainer: {
        marginRight: 5,
        flexDirection: 'column',
    },
    buttonItem:{
        paddingBottom:20,
    },
    icon: {
        width: '30%',
        height: '100%',
        borderRadius: 5,
    },
    centerContainer: {
        width: '46%',
        padding: 10,
    },
    rightContainer:{
        width: '24%',
        paddingTop: 15,
    },
    price: {
        color: '#ec655b'
    }
})
