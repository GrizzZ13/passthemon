package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.service.ImagesService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ImagesController {
    @Autowired
    private ImagesService imagesService;

    @RequestMapping("/images/getAllImgByGoodsId")
    public Msg getAllImgByGoodsId(@RequestParam(value = "goodsId") Integer goodsId){
        List<String> result = imagesService.getAllImgByGoodsId(goodsId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/images/getAllImgForThisPage")
    public Msg getAllImgForThisPage(@RequestBody JSONObject jsonObjects){
        List<Integer> ids = new ArrayList<>();
        JSONArray jsonArray = jsonObjects.getJSONArray("list");
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject tmp = jsonArray.getJSONObject(i);
            ids.add(tmp.getInteger("data"));
            System.out.println(tmp.getInteger("data"));
        }

        List<JSONObject> result = new ArrayList<>();
        for(Integer goodsId : ids){
            String cover = imagesService.getAllCoverForThisPage(goodsId);
            JSONObject json = new JSONObject();
            json.put("image", cover);
            json.put("goodsId", goodsId);
            result.add(json);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }
}
