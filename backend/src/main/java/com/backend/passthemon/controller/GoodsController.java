package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.annotation.RequestConsistent;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.dto.GoodsDto;
import com.backend.passthemon.dto.GoodsWanterDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static com.backend.passthemon.constant.GoodsConstant.BEING_AUDITED;
import static com.backend.passthemon.controller.WebSocketServer.userService;

@Slf4j
@RestController
@CrossOrigin
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HistoryService historyService;

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
    @RequestConsistent(identityConsistent = true)
    public Msg addGood(@RequestBody JSONObject param){
        try{
            String name = param.getString("name");
            Integer inventory = param.getInteger("inventory");
            String StringPrice = param.getString("price");
            String description = param.getString("description");
            BigDecimal price = new BigDecimal(StringPrice);
            String time = param.getString("upload_time");
            Timestamp uploadTime = Timestamp.valueOf(time);
            Integer userid = param.getInteger("userId");
            JSONArray images = param.getJSONArray("images");
            JSONArray tags = param.getJSONArray("labels");
            List<String> imgStrList = new ArrayList<>();
            for (Object image : images) {
                imgStrList.add((String) image);
            }
            //写入数据库，state为正在审核
            Goods goods = new Goods(price, name, inventory, description, uploadTime, new User(userid), BEING_AUDITED,(Integer) tags.get(0),(Integer) tags.get(1));
            goods = goodsService.addGood(goods);
            Integer goodsId = goods.getId();
            imagesService.addImg(imgStrList, goodsId);
            JSONObject product = new JSONObject();
            product.put("goodsId",goodsId);
            goodsProducer.sendGoods(product);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag",true);
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
        }
        catch(Exception e){
            return MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG, e.getMessage());
        }
    }
    @RequestMapping("/goods/removeGoods")
    @RequestConsistent(identityConsistent = true)
    public Msg removeGoods(@RequestParam("goodsId") Integer goodsId,@RequestParam("userId") Integer userId){
        if(goodsService.removeGoods(goodsId,userId).equals(1)) {
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
        }else {
            return MsgUtil.makeMsg(MsgUtil.PERMISSION_DENIED,MsgUtil.PERMISSION_DENIED_MSG);
        }
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
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
    }
    @RequestMapping("/goods/editGood")
    @RequestConsistent(identityConsistent = true)
    public Msg editGood(@RequestBody JSONObject param){
        Integer userId=param.getInteger("userId");
        Integer goodsId=param.getInteger("goodId");
        String name=param.getString("name");
        Integer inventory=param.getInteger("inventory");
        String StringPrice=param.getString("price");
        BigDecimal price=new BigDecimal(StringPrice);
        String description=param.getString("description");
        List<String> imageGroup=new ArrayList<>();
        JSONArray JsonImages=param.getJSONArray("images");
        JSONArray JsonTags=param.getJSONArray("labels");
        for (Object jsonImage : JsonImages) {
            imageGroup.add((String) jsonImage);
        }
        //修改商品信息并把状态改为审核中
        GoodInfoDto goodInfoDto=new GoodInfoDto(goodsId,description,inventory,name,price,imageGroup,(Integer) JsonTags.get(0),(Integer) JsonTags.get(1));
        if(goodsService.editGood(goodInfoDto,userId).equals(1)) {
            goodsService.changeState(BEING_AUDITED, goodsId);
            JSONObject product=new JSONObject();
            product.put("goodId",goodsId);
            goodsProducer.EditGoods(product);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", true);
            return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
        }else {
            return MsgUtil.makeMsg(MsgUtil.PERMISSION_DENIED,MsgUtil.PERMISSION_DENIED_MSG);
        }
    }

    @RequestMapping("/goods/getWanter")
    public Msg getWanter(@RequestParam("goodsId") Integer goodsId){
        List<Wants> wantsList = goodsService.findGoodsById(goodsId).getWantsList();
        List<GoodsWanterDto> result = GoodsWanterDto.convert(wantsList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);

        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
    }

    @RequestMapping("/goods/getMaxGoodsPage")
    public Msg getMaxGoodsPage(
            @RequestParam(value = "category") Integer category,
            @RequestParam(value = "attrition") Integer attrition){
        int maxPage = goodsService.getMaxGoodsPage(category, attrition);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", maxPage);
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject);
    }
}
