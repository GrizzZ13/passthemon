package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.HistoryDto;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.service.HistoryService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @RequestMapping("/history/listHistory")
    public Msg listHistory(
            @RequestParam("userId") Integer userId,
            @RequestParam("fetchPage") Integer fetchPage
    ){
        List<History> histories = historyService.listHistory(userId, fetchPage);
        List<HistoryDto> tmpList = HistoryDto.convert(histories);
        List<JSONObject> result = new ArrayList<>();
        List<HistoryDto> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        for(Integer i = 0; i < tmpList.size(); i++){
            HistoryDto history = tmpList.get(i);
            String ts = history.getTime().toString().substring(0, 10);
            if(jsonObject.isEmpty()){
                jsonObject.put("title", ts);
                list.add(history);
                continue;
            }
            if(ts.equals(jsonObject.getString("title"))){
                list.add(history);
                continue;
            }
            else{
                jsonObject.put("data", list);
                JSONObject tmpjson = new JSONObject();
                tmpjson.put("title", jsonObject.getString("title"));
                tmpjson.put("data", list);
                result.add(tmpjson);
                jsonObject.clear();
                list = null;
                list = new ArrayList<HistoryDto>();
                jsonObject.put("title", ts);
                list.add(history);
            }
        }
        if(!jsonObject.isEmpty() && (result.isEmpty() || !jsonObject.getString("title").equals(result.get(result.size() - 1).getString("title")))) {
            jsonObject.put("data", list);
            result.add(jsonObject);
        }
        System.out.println(result);
        JSONObject json = new JSONObject();
        json.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, json, request);

        return msg;
    }

    @RequestMapping("/history/listHistoryByTime")
    public Msg listHistoryByTime(@RequestParam("userId") Integer userId,
                                 @RequestParam(value = "fetchPage") Integer fetchPage,
                                 @RequestParam(value = "startTime", required = false) Timestamp startTime,
                                 @RequestParam(value = "endTime", required = false) Timestamp endTime)
    {
        List<History> histories = historyService.listHistoryByTime(userId, fetchPage, startTime, endTime);
        List<HistoryDto> tmpList = HistoryDto.convert(histories);
        List<JSONObject> result = new ArrayList<>();
        List<HistoryDto> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        for(Integer i = 0; i < tmpList.size(); i++){
            HistoryDto history = tmpList.get(i);
            String ts = history.getTime().toString().substring(0, 10);
            if(jsonObject.isEmpty()){
                jsonObject.put("title", ts);
                list.add(history);
                continue;
            }
            if(ts.equals(jsonObject.getString("title"))){
                list.add(history);
                continue;
            }
            else{
                jsonObject.put("data", list);
                JSONObject tmpjson = new JSONObject();
                tmpjson.put("title", jsonObject.getString("title"));
                tmpjson.put("data", list);
                result.add(tmpjson);
                jsonObject.clear();
                list = null;
                list = new ArrayList<HistoryDto>();
                jsonObject.put("title", ts);
                list.add(history);
            }
        }
        if(!jsonObject.isEmpty() && (result.isEmpty()
                || !jsonObject.getString("title").equals(result.get(result.size() - 1).getString("title")))) {
            jsonObject.put("data", list);
            result.add(jsonObject);
        }

        System.out.println(result.size());

        JSONObject json = new JSONObject();
        json.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, json, request);
        System.out.println("msg");
        return msg;
    }

    @RequestMapping("/history/deleteAllHistory")
    public Msg deleteAllHistory(@RequestParam("userId") Integer userId){
        historyService.deleteAllHistory(userId);
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);

        return msg;
    }
}
