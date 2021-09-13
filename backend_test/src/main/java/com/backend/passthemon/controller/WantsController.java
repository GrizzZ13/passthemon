package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.service.WantsService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class WantsController {
    @Autowired
    private WantsService wantsService;

    @RequestMapping("/wants/addWants")
    public Msg addWants(
            @RequestParam("buyerId") Integer buyerId,
            @RequestParam("sellerId") Integer sellerId,
            @RequestParam("goodsId") Integer goodsId,
            @RequestParam("num") Integer num
    ){
        wantsService.addWants(buyerId, sellerId, goodsId, num);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,null, request);

        return msg;
    }

    @RequestMapping("/wants/deleteWants")
    public Msg deleteWants(
            @RequestParam("buyerId") Integer buyerId,
            @RequestParam("sellerId") Integer sellerId,
            @RequestParam("goodsId") Integer goodsId
    ){
        wantsService.deleteWants(buyerId, sellerId, goodsId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,null, request);

        return msg;
    }

    @RequestMapping("/wants/checkWants")
    public Msg checkWants(
            @RequestParam("userId") Integer userId,
            @RequestParam("goodsId") Integer goodsId
    ){
        Wants result = wantsService.checkWants(userId, goodsId);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(result));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }
}
