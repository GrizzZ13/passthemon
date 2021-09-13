package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.annotation.RequestConsistent;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.dto.GoodsDto;
import com.backend.passthemon.dto.GoodsWanterDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.producer.GoodsProducer;
import com.backend.passthemon.service.*;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private  UserService userService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private GoodsProducer goodsProducer;

    @RequestMapping("/goods/listGoodsByPage")
    public Msg listGoodsByPages(
            @RequestParam(value = "fetchPage") Integer fetchPage,
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "category") Integer category,
            @RequestParam(value = "attrition") Integer attrition
    ){
        log.info("[GoodsController]");
        System.out.println("fetchPage");
        System.out.println(fetchPage);
        JSONObject jsonObject = goodsService.listGoodsByPages(fetchPage, userId, category, attrition);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }
    @RequestMapping("/goods/listMyGoodsByPage")
    public Msg listMyGoodsByPages(
            @RequestParam(value = "fetchPage") Integer fetchPage,
            @RequestParam(value = "userId") Integer userId
    ){
        log.info("[GoodsController]");
        System.out.println(userId);
        List<GoodsDto> result = goodsService.listMyGoodsByPages(fetchPage, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/goods/findGoodsById")
    public Msg findGoodsById(
            @RequestParam("goodsId") Integer goodsId,
            @RequestParam("userId") Integer userId
    ){
        historyService.addHistory(userId,goodsId);
        Goods result = goodsService.findGoodsById(goodsId);

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(result));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }
    @RequestMapping("/goods/getGoodsById")
    public Msg getGoodsById(@RequestParam("goodId") Integer goodsId){
        Goods result = goodsService.findGoodsById(goodsId);
        result.setImagesList(imagesService.getAllImgByGoodsId(goodsId));
        GoodInfoDto goodInfoDto=new GoodInfoDto(result);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(goodInfoDto));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
        return msg;
    }
    @RequestMapping("/goods/addGood")
    public Msg addGood(@RequestBody JSONObject param){
        goodsProducer.sendGoods(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject, request);
        return msg;
    }
    @RequestMapping("/goods/removeGoods")
    @RequestConsistent(identityConsistent = true)
    public Msg removeGoods(@RequestParam("goodsId") Integer goodsId){
        goodsService.removeGoods(goodsId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,null, request);
        return msg;
    }

    @RequestMapping("/goods/searchGoods")
    public Msg searchGoods(
            @RequestParam("search") String search,
            @RequestParam("fetchPage") Integer fetchPage
    ){
        List<Goods> goodsList = goodsService.searchGoods(search, fetchPage);
        List<GoodsDto> result = GoodsDto.convert(goodsList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
        System.out.println(msg);
        return msg;
    }
    @RequestMapping("/goods/editGood")
    public Msg editGood(@RequestBody JSONObject param){
        goodsProducer.EditGoods(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject, request);

        return msg;
    }

    @RequestMapping("/goods/getWanter")
    public Msg getWanter(@RequestParam("goodsId") Integer goodsId){
        List<Wants> wantsList = goodsService.findGoodsById(goodsId).getWantsList();
        List<GoodsWanterDto> result = GoodsWanterDto.convert(wantsList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/goods/getMaxGoodsPage")
    public Msg getMaxGoodsPage(
            @RequestParam(value = "category") Integer category,
            @RequestParam(value = "attrition") Integer attrition){
        int maxPage = goodsService.getMaxGoodsPage(category, attrition);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", maxPage);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject, request);

        return msg;
    }
}
