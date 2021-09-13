package com.backend.passthemon.producer;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.backend.passthemon.constant.RabbitmqConstant.*;
@Component
public class DemandProducer {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void sendDemand(JSONObject jsonObject){
        this.rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME,QUEUE_DEMAND_BINDING_KEY,jsonObject);
    }
    public void editDemand(JSONObject jsonObject){
        this.rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME,QUEUE_EDIT_DEMAND_BINDING_KEY,jsonObject);
    }
}
