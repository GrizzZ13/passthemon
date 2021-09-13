package com.backend.passthemon.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.backend.passthemon.constant.RabbitmqConstant.*;
@Component
public class GoodsProducer {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void sendGoods(JSONObject jsonObject){
        this.rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME,QUEUE_GOODS_BINDING_KEY,jsonObject);
    }
    public void EditGoods(JSONObject jsonObject){
        this.rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME,QUEUE_EDIT_GOODS_BINDING_KEY,jsonObject);
    }

}
