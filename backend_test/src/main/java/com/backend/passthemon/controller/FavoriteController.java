package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.FavoriteDto;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.service.FavoriteService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/Favorite/listFavoriteByPage")
    public Msg listFavoriteByPage(
            @RequestParam("fetchPage") Integer fetchPage,
            @RequestParam("userId") Integer userId
    ){
        List<FavoriteDto> result = favoriteService.listFavoriteByPage(fetchPage, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/Favorite/addFavorite")
    public Msg addFavorite(
            @RequestParam("userId") Integer userId,
            @RequestParam("goodsId") Integer goodsId
    ){
        Favorite result = favoriteService.addFavorite(userId, goodsId);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(result));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/Favorite/deleteFavorite")
    public Msg deleteFavorite(
            @RequestParam("userId") Integer userId,
            @RequestParam("goodsId") Integer goodsId
    ){
        Favorite result = favoriteService.deleteFavorite(userId, goodsId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, null, request);

        return msg;
    }

    @RequestMapping("/Favorite/deleteAllFavorite")
    public Msg deleteAllFavorite(@RequestParam("userId") Integer userId){
        Favorite result = favoriteService.deleteAllFavorite(userId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, null, request);

        return msg;
    }

    @RequestMapping("/Favorite/checkFavorite")
    public Msg checkFavorite(
            @RequestParam("userId") Integer userId,
            @RequestParam("goodsId") Integer goodsId
    ){
        Favorite result = favoriteService.checkFavorite(userId, goodsId);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(result));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }
}
