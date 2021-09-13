package com.backend.passthemon.service;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.dto.GoodsDto;
import com.backend.passthemon.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

public interface GoodsService {
    JSONObject listGoodsByPages(Integer fetchPage, Integer userId, Integer category, Integer attrition);
    List<GoodsDto> listMyGoodsByPages(Integer fetchPage, Integer userId);
    List<Goods> searchGoods(String search, Integer fetchPage);
    Goods findGoodsById(Integer goodsId);
    Integer addGood(Goods goods);
    void editGood(GoodInfoDto goodInfoDto);
    void removeGoods(Integer goodsId);
    void changeState(Integer state,Integer goodsId);
    Integer getMaxGoodsPage(Integer category, Integer attrition);
}
