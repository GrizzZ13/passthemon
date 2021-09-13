package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.service.ImagesService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ImagesController {
    @Autowired
    private ImagesService imagesService;

    @RequestMapping("/images/getAllImgByGoodsId")
    public Msg getAllImgByGoodsId(@RequestParam(value = "goodsId") Integer goodsId){
        List<Images> result = imagesService.getAllImgByGoodsId(goodsId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);

        return msg;
    }

    @RequestMapping("/images/getAllImgForThisPage")
    public Msg getAllImgForThisPage(@RequestParam(value = "list") String strList){
        System.out.println(strList);
        int lastIndex = 0;
        List<Integer> ids = new ArrayList<>();
        for(int i = 0; i < strList.length(); i++){
            if(strList.charAt(i) == ','){
                Integer id = Integer.parseInt(strList.substring(lastIndex, i));
                ids.add(id);
                lastIndex = i + 1;
            }
            if(i == strList.length() - 1){
                Integer id = Integer.parseInt(strList.substring(lastIndex, i + 1));
                ids.add(id);
                break;
            }
        }
        List<JSONObject> result = new ArrayList<>();

        for(Integer goodsId : ids){
            List<Images> images = imagesService.getAllImgByGoodsId(goodsId);
            JSONObject json = new JSONObject();
            json.put("image", images.get(0).getImg());
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
