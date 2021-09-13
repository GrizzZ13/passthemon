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
            String name = param.getString("name");
            Integer num = param.getInteger("num");
            String StringPrice = param.getString("idealPrice");
            BigDecimal idealPrice = new BigDecimal(StringPrice);
            String time = param.getString("upload_time");
            Timestamp uploadTime = Timestamp.valueOf(time);
            Integer userid = param.getInteger("userId");
            String description = param.getString("description");
            JSONArray tags = param.getJSONArray("labels");
            //写入数据库，state为审核中
            User user = userService.getUserByUserId(userid);
            // public Demand(String name,Integer num,BigDecimal idealPrice,String description,Timestamp upLoadTime,User user)
            Demand demand = new Demand(name, num, idealPrice, description, uploadTime, user, BEING_AUDITED,(Integer)tags.get(0),(Integer)tags.get(1));
            Integer demandId = demandService.addDemand(demand);
            demand.setId(demandId);
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
