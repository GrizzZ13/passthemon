package com.backend.passthemon.consumer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.DemandService;
import com.backend.passthemon.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.backend.passthemon.constant.GoodsConstant.*;
import static com.backend.passthemon.constant.RabbitmqConstant.*;
@Component
@RabbitListener(queues = QUEUE_DEMAND_NAME)
public class DemandConsumer {

    @Autowired
    UserService userService;

    @Autowired
    DemandService demandService;

    @Autowired
    TextCensor textCensor;
    @RabbitHandler
    public void handleDemand(JSONObject param) {
        try {
            Integer demandId=param.getInteger("demandId");
            Demand demand=demandService.getDemandById(demandId);
            String name=demand.getName();
            String description=demand.getDescription();
            //进行审核
            Integer state = ON_SALE;
            String nameResult = textCensor.TextCensor(name,3);
            JSONObject nameResultJson = JSONObject.parseObject(nameResult);
            if (nameResultJson.getString("conclusion").equals("不合规")) {
                state = TEXT_FAILED_AUDIT;
            }else {
                String descriptionResult = textCensor.TextCensor(description, 3);
                JSONObject descriptionResultJson = JSONObject.parseObject(descriptionResult);
                if (descriptionResultJson.getString("conclusion").equals("不合规")) {
                    state = TEXT_FAILED_AUDIT;
                }
            }
            //根据审核结果改变State，重新写入数据库
            demandService.changeState(demandId, state);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
