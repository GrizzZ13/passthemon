package com.backend.passthemon.consumer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.service.GoodsService;
import com.backend.passthemon.service.ImagesService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.backend.passthemon.constant.GoodsConstant.*;
import static com.backend.passthemon.constant.RabbitmqConstant.*;
@Component
@RabbitListener(queues = QUEUE_EDIT_GOODS_NAME)
public class EditGoodsConsumer {
    @Autowired
    GoodsService goodsService;
    @Autowired
    ImagesService imagesService;
    @Autowired
    ImgCensor imgCensor;
    @Autowired
    TextCensor textCensor;
    @RabbitHandler
    public void handleEditGoods(JSONObject param){
        try{
            Integer goodsId=param.getInteger("goodId");
            Goods goods=goodsService.findGoodsById(goodsId);
            List<String> imageGroup=imagesService.getAllImgByGoodsId(goodsId);
            String name=goods.getName();
            String description=goods.getDescription();
            //进行审核
            List<String> imgResult = imgCensor.ImgCensor(imageGroup,1);
            Integer state = ON_SALE;
            for (String it : imgResult) {
                JSONObject item = JSONObject.parseObject(it);
                String result = item.getString("conclusion");
                if (result.equals("不合规")) {
                    state = IMAGE_FAILED_AUDIT;
                    break;
                }
            }
            //图片未违规则进入文本审核
            if (state.equals(ON_SALE)) {
                String nameResult = textCensor.TextCensor(name,1);
                JSONObject nameResultJson = JSONObject.parseObject(nameResult);
                if (nameResultJson.getString("conclusion").equals("不合规")) {
                    state = TEXT_FAILED_AUDIT;
                }else {
                    String descriptionResult = textCensor.TextCensor(description, 1);
                    JSONObject descriptionResultJson = JSONObject.parseObject(descriptionResult);
                    if (descriptionResultJson.getString("conclusion").equals("不合规")) {
                        state = TEXT_FAILED_AUDIT;
                    }
                }
            }
            //根据审核结果改变State
            goodsService.changeState(state, goodsId);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
