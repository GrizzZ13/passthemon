package com.backend.passthemon.consumer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.service.DemandService;
import com.backend.passthemon.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.backend.passthemon.constant.GoodsConstant.*;
import static com.backend.passthemon.constant.RabbitmqConstant.*;

@Component
@RabbitListener(queues = QUEUE_EDIT_DEMAND_NAME)
public class EditDemandConsumer {
    @Autowired
    UserService userService;

    @Autowired
    DemandService demandService;


    @Autowired
    TextCensor textCensor;
    @RabbitHandler
    public void handleEditDemand(JSONObject param){
        try {
            Integer demandId = param.getInteger("demandId");
            String name = param.getString("name");
            Integer num = param.getInteger("num");
            String description = param.getString("description");
            String StringPrice = param.getString("idealPrice");
            BigDecimal idealPrice = new BigDecimal(StringPrice);
            JSONArray JsonLabels = param.getJSONArray("labels");
            DemandInfoDto demandInfoDto = new DemandInfoDto(demandId, name, num, idealPrice, description, (Integer) JsonLabels.get(0),(Integer) JsonLabels.get(1));
            demandService.editDemand(demandInfoDto);
            //修改state为审核中
            demandService.changeState(demandId, BEING_AUDITED);
            //进行审核
            Integer state = ON_SALE;
            String nameResult = textCensor.TextCensor(name,2);
            JSONObject nameResultJson = JSONObject.parseObject(nameResult);
            if (nameResultJson.getString("conclusion").equals("不合规")) {
                state = TEXT_FAILED_AUDIT;
            }else {
            String descriptionResult = textCensor.TextCensor(description,2);
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
