package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.AllDemandDto;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.service.WantsService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WantsController.class)
class WantsControllerUnitTest {
    @MockBean
    private WantsService wantsService;

    @Autowired
    private MockMvc mvc;

    private Wants wants;

    @Test
    void addWants() {
        Integer buyerId = 1, sellerId = 2, goodsId = 3, num = 4;
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/wants/addWants?buyerId=" + buyerId.toString()
            + "&sellerId=" + sellerId.toString() + "&goodsId=" + goodsId.toString() + "&num=" + num.toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void deleteWants() {
        Integer buyerId = 1, sellerId = 2, goodsId = 3;
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/wants/deleteWants?buyerId=" + buyerId.toString()
                    + "&sellerId=" + sellerId.toString() + "&goodsId=" + goodsId.toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void checkWants() {
        Integer userId = 1, goodsId = 1;

        /* 给出预测行为 */
        given(this.wantsService.checkWants(1, 1)).willReturn(wants);

        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(wants));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/wants/checkWants/?userId=" + userId
            +"&goodsId=" + goodsId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
