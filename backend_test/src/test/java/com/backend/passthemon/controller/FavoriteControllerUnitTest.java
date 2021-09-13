package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.FavoriteDto;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.service.FavoriteService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerUnitTest {
    @MockBean
    private FavoriteService favoriteService;

    @Autowired
    private MockMvc mvc;

    private List<FavoriteDto> favoriteList;

    private final Favorite favorite = new Favorite();

    @Test
    void listFavoriteByPage() {
        Integer fetchPage = 0;
        Integer userId = 1;

        /* 给出预测行为 */
        given(this.favoriteService.listFavoriteByPage(fetchPage, userId)).willReturn(favoriteList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", favoriteList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/Favorite/listFavoriteByPage?fetchPage=" + fetchPage
                + "&userId=" + userId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void addFavorite() {
        Integer userId = 1, goodsId = 1;
        given(this.favoriteService.addFavorite(userId, goodsId)).willReturn(favorite);
        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(favorite));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/Favorite/addFavorite?userId=" + userId
                    + "&goodsId=" + goodsId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void checkFavorite() {
        Integer userId = 1, goodsId = 1;
        given(this.favoriteService.checkFavorite(userId, goodsId)).willReturn(favorite);
        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(favorite));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/Favorite/checkFavorite?userId=" + userId
                    + "&goodsId=" + goodsId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void deleteFavorite() {
        Integer userId = 1, goodsId = 1;
        given(this.favoriteService.deleteFavorite(userId, goodsId)).willReturn(favorite);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, (JSONObject) null);
            mvc.perform(MockMvcRequestBuilders.get("/Favorite/deleteFavorite?userId=" + userId
                    + "&goodsId=" + goodsId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void deleteAllFavorite() {
        Integer userId = 1;
        given(this.favoriteService.deleteAllFavorite(userId)).willReturn(favorite);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, (JSONObject) null);
            mvc.perform(MockMvcRequestBuilders.get("/Favorite/deleteAllFavorite?userId=" + userId
                    ))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
