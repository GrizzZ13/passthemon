package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.AllDemandDto;
import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.producer.DemandProducer;
import com.backend.passthemon.service.DemandService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class DemandController {
    @Autowired
    DemandService demandService;

    @Autowired
    UserService userService;

    @Autowired
    DemandProducer demandProducer;

    @RequestMapping("/demand/addDemand")
    public Msg addDemand(@RequestBody JSONObject param){
        demandProducer.sendDemand(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject, request);
        return msg;
    }

    @RequestMapping("/demand/removeDemand")
    public Msg removeDemand(@RequestParam(value="demandId") Integer demandId){
        demandService.removeDemand(demandId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,null, request);
        return msg;
    }


    @RequestMapping("/demand/listAllDemandsByPage")
    public Msg listAllDemandsByPage(
            @RequestParam(value = "fetchPage") Integer fetchPage,
            @RequestParam(value = "category") Integer category,
            @RequestParam(value = "attrition") Integer attrition
    ){
        List<Demand> demandList = demandService.listAllDemandsByPage(fetchPage, category, attrition);
        List<AllDemandDto> result = AllDemandDto.convert(demandList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/demand/listOnesDemandsByPage")
    public Msg listOnesDemandsByPage(
            @RequestParam(value = "fetchPage") Integer fetchPage,
            @RequestParam(value = "userId") Integer userId
    ){
        List<Demand> demandList = demandService.listOnesDemandsByPage(fetchPage, userId);
        List<AllDemandDto> result = AllDemandDto.convert(demandList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/demand/getDemandById")
    public Msg getDemandById(@RequestParam("demandId") Integer demandId){
        Demand demand=demandService.getDemandById(demandId);
        DemandInfoDto demandInfoDto=new DemandInfoDto(demand);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(demandInfoDto));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
        return msg;
    }
    @RequestMapping("/demand/editDemand")
    public Msg editDemand(@RequestBody JSONObject param){
        demandProducer.editDemand(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject, request);
        return msg;
    }
}
