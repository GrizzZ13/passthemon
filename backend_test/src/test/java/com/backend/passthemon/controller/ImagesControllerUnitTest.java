package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.AllDemandDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.service.ImagesService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImagesController.class)
class ImagesControllerUnitTest {
    @MockBean
    private ImagesService imagesService;

    @Autowired
    private MockMvc mvc;

    private List<Images> imagesList = new ArrayList<>();
    private Images images = new Images();

    @Test
    void getAllImgByGoodsId() {
        Integer goodsId = 1;

        /* 给出预测行为 */
        given(this.imagesService.getAllImgByGoodsId(goodsId)).willReturn(imagesList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", imagesList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/images/getAllImgByGoodsId/?goodsId=" + goodsId))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void getAllImgForThisPage() {
        String list = "1,2";
        Integer goodsId = 1;

        /* 给出预测行为 */
        given(this.imagesService.getAllImgByGoodsId(goodsId)).willReturn(imagesList);
        imagesList.add(images);
        images.setImg("img");
        goodsId = 2;
        given(this.imagesService.getAllImgByGoodsId(goodsId)).willReturn(imagesList);
        imagesList.add(images);
        images.setImg("img");

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", imagesList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/images/getAllImgForThisPage/?list=" + list))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
