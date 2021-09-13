package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.dto.GoodsDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.producer.GoodsProducer;
import com.backend.passthemon.service.GoodsService;
import com.backend.passthemon.service.HistoryService;
import com.backend.passthemon.service.ImagesService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import groovy.transform.Undefined;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoodsController.class)
class GoodsControllerUnitTest {
    @MockBean
    private GoodsService goodsService;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private UserService userService;

    @MockBean
    private ImagesService imagesService;

    @MockBean
    private GoodsProducer goodsProducer;

    @Autowired
    private MockMvc mvc;

    private List<Goods> goodsList;

    private List<GoodsDto> goodsDtoList;

    List<Wants> wantsList;

    private int maxPage = 1;

    private Goods goods = new Goods(1, null);

    @Test
    void listGoodsByPages() {
        Integer fetchPage = 0;
        Integer userId = 1;
        Integer category = 0;
        Integer attrition = 0;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", goodsList);
        jsonObject.put("maxPage", 0);
        jsonObject.put("currentPage", 0);
        given(this.goodsService.listGoodsByPages(fetchPage, userId, category, attrition)).willReturn(jsonObject);

        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/goods/listGoodsByPage?fetchPage=" + fetchPage
                    + "&userId=" + userId + "&category=" + category + "&attrition=" + attrition))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void listMyGoodsByPages() {
        Integer fetchPage = 0;
        Integer userId = 1;
        given(this.goodsService.listMyGoodsByPages(fetchPage, userId)).willReturn(goodsDtoList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", goodsDtoList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/goods/listMyGoodsByPage?fetchPage=" + fetchPage
                    + "&userId=" + userId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void findGoodsById() {
        Integer goodsId = 1;
        Integer userId = 1;
        given(this.goodsService.findGoodsById(goodsId)).willReturn(goods);

        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(goods));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/goods/findGoodsById?goodsId=" + goodsId
                    + "&userId=" + userId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void getGoodsById() {
        Integer goodsId = 1;
        goods.setDescription("12");
        goods.setInventory(1);
        goods.setPrice(BigDecimal.valueOf(1));
        goods.setName("wad");
        goods.setCategory(1);
        goods.setAttrition(1);
        List<Images> imgList=new ArrayList<>();
        goods.setImagesList(imgList);
        given(this.goodsService.findGoodsById(goodsId)).willReturn(goods);

        try {
            GoodInfoDto goodInfoDto = new GoodInfoDto(goods);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(goodInfoDto));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            System.out.println(msg.getData());
            mvc.perform(MockMvcRequestBuilders.get("/goods/getGoodsById?goodId=" + goodsId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data.goodId").value(msg.getData().getIntValue("goodId")));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void addGood() {
        Integer goodsId=1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        JSONObject value = JSONObject.parseObject(JSONObject.toJSONString(goods));
        given(this.goodsService.addGood(goods)).willReturn(goodsId);
        try{
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject);
            mvc.perform(MockMvcRequestBuilders.post("/goods/addGood").content(value.toString()).contentType("application/json"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void removeGoods() {

        Mockito.doNothing().when(goodsService).removeGoods(Mockito.anyInt());
        Integer goodsId=1;
        try{
            mvc.perform(MockMvcRequestBuilders.get("/goods/removeGoods?goodsId=" + goodsId));
            Mockito.verify(goodsService,Mockito.times(1)).removeGoods(goodsId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void searchGoods() {
        List<Goods> goodsList = new ArrayList<>();
        Integer fetchPage = 0;
        String search = "";
        Goods goods = new Goods(1);
        goods.setPrice(BigDecimal.valueOf(121));
        goods.setName("11");
        goods.setState(1);
        List<Wants> wantsList = new ArrayList<>();
        User user = new User(1);
        goods.setUser(user);
        goods.setWantsList(wantsList);
        goodsList.add(goods);
        given(this.goodsService.searchGoods(search, fetchPage)).willReturn(goodsList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", GoodsDto.convert(goodsList));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/goods/searchGoods?fetchPage=" + fetchPage
                    + "&search=" + search))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(null));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void editGood() {
        Integer goodsId=1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",true);
        JSONObject value = JSONObject.parseObject(JSONObject.toJSONString(goods));
        given(this.goodsService.addGood(goods)).willReturn(goodsId);
        try{
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG,jsonObject);
            mvc.perform(MockMvcRequestBuilders.post("/goods/editGood").content(value.toString()).contentType("application/json"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getWanter() {
        Integer goodsId = 1;
        Wants wants = new Wants();
        wants.setId(1);
        User user = new User(1);
        user.setUsername("11");
        wants.setNum(1);
        wants.setTimestamp(Timestamp.valueOf("2021-01-01 01:01:01"));
        List<Wants> wantsList = new ArrayList<>();
        wantsList.add(wants);
        wants.setBuyer(user);
        goods.setWantsList(wantsList);
        given(this.goodsService.findGoodsById(goodsId)).willReturn(goods);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", wantsList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/goods/getWanter?goodsId=" + goodsId))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(null));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    void getMaxGoodsPage() {
        Integer category = 0;
        Integer attrition = 0;
        given(this.goodsService.getMaxGoodsPage(category, attrition)).willReturn(maxPage);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", maxPage);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/goods/getMaxGoodsPage?category=" + category
                    + "&attrition=" + attrition))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
