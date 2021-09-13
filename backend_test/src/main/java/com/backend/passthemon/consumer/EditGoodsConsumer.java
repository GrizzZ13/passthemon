package com.backend.passthemon.consumer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.service.GoodsService;
import com.backend.passthemon.service.ImagesService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            String name=param.getString("name");
            Integer inventory=param.getInteger("inventory");
            String StringPrice=param.getString("price");
            BigDecimal price=new BigDecimal(StringPrice);
            String description=param.getString("description");
            List<String> imageGroup=new ArrayList<>();
            JSONArray JsonImages=param.getJSONArray("images");
            JSONArray JsonTags=param.getJSONArray("labels");
            for(int i=0;i<JsonImages.size();i++){
                imageGroup.add((String)JsonImages.get(i));
            }
            //修改商品信息并把状态改为审核中
            GoodInfoDto goodInfoDto=new GoodInfoDto(goodsId,description,inventory,name,price,imageGroup,(Integer) JsonTags.get(0),(Integer) JsonTags.get(1));
            goodsService.editGood(goodInfoDto);
            goodsService.changeState(BEING_AUDITED,goodsId);
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
