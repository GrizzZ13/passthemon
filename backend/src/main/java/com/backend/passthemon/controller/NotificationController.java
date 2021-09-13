package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.annotation.RequestAuthority;
import com.backend.passthemon.constant.AuthorityConstant;
import com.backend.passthemon.dto.NotificationDto;
import com.backend.passthemon.entity.Notification;
import com.backend.passthemon.service.KeyConstantService;
import com.backend.passthemon.service.NotificationService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.backend.passthemon.constant.NotificationConstant.ORDINARY_TYPE;
import static com.backend.passthemon.constant.NotificationConstant.STATUS_CODE;

@Slf4j
@RestController
@CrossOrigin
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    KeyConstantService keyConstantService;

    @RequestAuthority(value = {AuthorityConstant.ADMIN})
    @RequestMapping("/notification/publish")
    public Msg publishNotification(@RequestBody JSONObject params){
        try{
            String title = params.getString("title");
            String content = params.getString("content");
            Integer type = params.getInteger("type");
            Date date, expired;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // parse current date
            String dateStr = params.getString("date");
            if (dateStr==null){
                date = new Date();
            }
            else{
                date = sdf.parse(dateStr);
            }
            // parse expired date
            String expiredStr = params.getString("expired");
            if (expiredStr==null) {
                expired = new Date();
                long expiredMillis = date.getTime() + 1000 * 60 * 60 * 24 * 14;
                expired.setTime(expiredMillis);
            }
            else{
                expired = sdf.parse(expiredStr);
            }
            notificationService.publishNotification(title, content, type, date, expired);
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
        }
        catch (ParseException exception) {
            return MsgUtil.makeMsg(MsgUtil.ERROR, "date format should be [yyyy-MM-dd HH:mm]");
        }
        catch (Exception exception){
            return MsgUtil.makeMsg(MsgUtil.ERROR, exception.getMessage());
        }
    }


    @RequestMapping("/notification/getAllOrdinary")
    public Msg getAllOrdinary(){
        List<Notification> notifications = notificationService.getAllOrdinary();
        List<NotificationDto> notificationDtoList = NotificationDto.convert(notifications);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", notificationDtoList);
        jsonObject.put("value",keyConstantService.getValueByName(STATUS_CODE));
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
    }

    @RequestMapping("/notification/getDialog")
    public Msg getDialog(){
        List<Notification> notifications = notificationService.getAllDialog();
        if (notifications.size()==0){
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
        }
        else{
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(NotificationDto.convert(notifications.get(0)));
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
        }
    }

    @RequestMapping("/notification/checkStatusCode")
    public Msg checkStatusCode(@RequestBody JSONObject jsonObject){
        //判断value是否是最新的
        String name = jsonObject.getString("name");
        String value = jsonObject.getString("value");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("flag",keyConstantService.checkStatusCode(name,value));
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject1);
    }
}
