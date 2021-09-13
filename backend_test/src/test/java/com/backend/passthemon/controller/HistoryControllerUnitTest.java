package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.HistoryDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.HistoryService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryController.class)
class HistoryControllerUnitTest {
    @MockBean
    private HistoryService historyService;

    @Autowired
    private MockMvc mvc;

    private List<History> historyList = new ArrayList<>();

    @Test
    void listHistory() {
        List<History> histories = new ArrayList<>();
        Integer fetchPage = 0;
        Integer userId = 1;
        Integer goodsId = 1;
        User user = new User(goodsId);
        Goods goods = new Goods(goodsId);
        goods.setUser(user);
        goods.setName("123");
        goods.setPrice(BigDecimal.valueOf(10));
        goods.setId(1);
        Goods goods2 = new Goods(goodsId);
        goods2.setUser(user);
        goods2.setName("123");
        goods2.setPrice(BigDecimal.valueOf(10));
        goods2.setId(2);
        Goods goods3 = new Goods(goodsId);
        goods3.setUser(user);
        goods3.setName("123");
        goods3.setPrice(BigDecimal.valueOf(10));
        goods3.setId(3);
        History history = new History();
        History history2 = new History();
        History history3 = new History();
        history.setUser(user);
        history.setGoods(goods);
        history.setTime(Timestamp.valueOf("2021-06-06 00:00:00"));
        history2.setUser(user);
        history2.setGoods(goods3);
        history2.setTime(Timestamp.valueOf("2021-06-06 00:00:00"));
        history3.setUser(user);
        history3.setGoods(goods3);
        history3.setTime(Timestamp.valueOf("2021-06-07 00:00:00"));
        histories.add(history);
        histories.add(history2);
        histories.add(history3);
        given(this.historyService.listHistory(userId, fetchPage)).willReturn(histories);

        try {
            JSONObject jsonObject = new JSONObject();
            List<JSONObject> result = new ArrayList<>();
            jsonObject.put("list", histories);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/history/listHistory?fetchPage=" + fetchPage
                    + "&userId=" + userId))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void listHistoryByTime() {
        List<History> histories = new ArrayList<>();
        Integer fetchPage = 0;
        Integer userId = 1;
        Integer goodsId = 1;
        User user = new User(goodsId);
        Goods goods = new Goods(goodsId);
        goods.setUser(user);
        goods.setName("123");
        goods.setPrice(BigDecimal.valueOf(10));
        goods.setId(1);
        Goods goods2 = new Goods(goodsId);
        goods2.setUser(user);
        goods2.setName("123");
        goods2.setPrice(BigDecimal.valueOf(10));
        goods2.setId(2);
        Goods goods3 = new Goods(goodsId);
        goods3.setUser(user);
        goods3.setName("123");
        goods3.setPrice(BigDecimal.valueOf(10));
        goods3.setId(3);
        History history = new History();
        History history2 = new History();
        History history3 = new History();
        history.setUser(user);
        history.setGoods(goods);
        history.setTime(Timestamp.valueOf("2021-06-06 00:00:00"));
        history2.setUser(user);
        history2.setGoods(goods3);
        history2.setTime(Timestamp.valueOf("2021-06-06 00:00:00"));
        history3.setUser(user);
        history3.setGoods(goods3);
        history3.setTime(Timestamp.valueOf("2021-06-07 00:00:00"));
        histories.add(history);
        histories.add(history2);
        histories.add(history3);
        Timestamp startTime = Timestamp.valueOf("2021-05-06 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2021-08-26 23:59:59");
        given(this.historyService.listHistoryByTime(userId, fetchPage, startTime, endTime)).willReturn(histories);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", histories);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/history/listHistoryByTime?fetchPage=" + fetchPage
                    + "&userId=" + userId + "&startTime=" + startTime + "&endTime=" + endTime))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteAllHistory() {
        Integer userId = 1;
        given(this.historyService.deleteAllHistory(userId)).willReturn(1);

        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/history/deleteAllHistory?userId=" + userId))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
