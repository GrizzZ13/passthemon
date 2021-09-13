package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.annotation.RequestConsistent;
import com.backend.passthemon.dto.AllDemandDto;
import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static com.backend.passthemon.constant.GoodsConstant.BEING_AUDITED;

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
    @RequestConsistent(identityConsistent = true)
    public Msg addDemand(@RequestBody JSONObject param){
        try{
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
            Demand demand = new Demand(name, num, idealPrice, description, uploadTime, new User(userid), BEING_AUDITED,(Integer)tags.get(0),(Integer)tags.get(1));
            demand = demandService.addDemand(demand);
            JSONObject product=new JSONObject();
            product.put("demandId",demand.getId());
            demandProducer.sendDemand(product);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag",true);
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
        }
        catch(Exception e) {
            return MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG, e.getMessage());
        }
    }

    @RequestMapping("/demand/removeDemand")
    @RequestConsistent(identityConsistent = true)
    public Msg removeDemand(@RequestParam(value="demandId") Integer demandId,@RequestParam(value="userId") Integer userId){
        if(demandService.removeDemand(demandId,userId).equals(1)){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,null, request);
            return msg;
        }else {
            Msg msg = MsgUtil.makeMsg(MsgUtil.PERMISSION_DENIED,MsgUtil.PERMISSION_DENIED_MSG);
            return msg;
        }
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
    @RequestConsistent(identityConsistent = true)
    public Msg editDemand(@RequestBody JSONObject param){
        Integer userId = param.getInteger("userId");
        Integer demandId = param.getInteger("demandId");
        String name = param.getString("name");
        Integer num = param.getInteger("num");
        String description = param.getString("description");
        String StringPrice = param.getString("idealPrice");
        BigDecimal idealPrice = new BigDecimal(StringPrice);
        JSONArray JsonLabels = param.getJSONArray("labels");
        DemandInfoDto demandInfoDto = new DemandInfoDto(demandId, name, num, idealPrice, description, (Integer) JsonLabels.get(0),(Integer) JsonLabels.get(1));
        if(demandService.editDemand(demandInfoDto,userId).equals(1)){
        //修改state为审核中
        demandService.changeState(demandId, BEING_AUDITED);
        JSONObject product=new JSONObject();
        product.put("demandId",demandId);
        demandProducer.editDemand(product);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject, request);
        return msg;
        }
        else {
            Msg msg = MsgUtil.makeMsg(MsgUtil.PERMISSION_DENIED,MsgUtil.PERMISSION_DENIED_MSG);
            return msg;
        }
    }
}
