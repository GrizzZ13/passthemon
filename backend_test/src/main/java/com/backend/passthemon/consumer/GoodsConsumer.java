package com.backend.passthemon.consumer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.GoodsService;
import com.backend.passthemon.service.ImagesService;
import com.backend.passthemon.service.UserService;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.backend.passthemon.constant.GoodsConstant.*;
import static com.backend.passthemon.constant.RabbitmqConstant.*;
@Component
@RabbitListener(queues = QUEUE_GOODS_NAME)
public class GoodsConsumer {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ImagesService imagesService;
    @Autowired
    ImgCensor imgCensor;
    @Autowired
    TextCensor textCensor;
    @RabbitHandler
    public void handleGoods(JSONObject param){
        try {
            String name = param.getString("name");
            Integer inventory = param.getInteger("inventory");
            String StringPrice = param.getString("price");
            String description = param.getString("description");
            BigDecimal price = new BigDecimal(StringPrice);
            String time = param.getString("upload_time");
            Timestamp uploadTime = Timestamp.valueOf(time);
            Integer userid = param.getInteger("userId");
            JSONArray images = param.getJSONArray("images");
            JSONArray tags = param.getJSONArray("labels");
            List<String> imgStrList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                imgStrList.add((String) images.get(i));
            }
            //写入数据库，state为正在审核
            User user = userService.getUserByUserId(userid);
            Goods goods = new Goods(price, name, inventory, description, uploadTime, user, BEING_AUDITED,(Integer) tags.get(0),(Integer) tags.get(1));
            Integer goodsId = goodsService.addGood(goods);
            goods.setId(goodsId);
            for (int i = 0; i < images.size(); i++) {
                imagesService.addImg((String) images.get(i), goodsId, i + 1);
            }
            //进行审核
            List<String> imgResult = imgCensor.ImgCensor(imgStrList,0);
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
                String nameResult = textCensor.TextCensor(name,0);
                JSONObject nameResultJson = JSONObject.parseObject(nameResult);
                if (nameResultJson.getString("conclusion").equals("不合规")) {
                    state = TEXT_FAILED_AUDIT;
                }else {
                    String descriptionResult = textCensor.TextCensor(description, 0);
                    JSONObject descriptionResultJson = JSONObject.parseObject(descriptionResult);
                    if (descriptionResultJson.getString("conclusion").equals("不合规")) {
                        state = TEXT_FAILED_AUDIT;
                    }
                }
            }
            //根据审核结果改变State
            goodsService.changeState(state, goodsId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
