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
    Goods addGood(Goods goods);
    Integer editGood(GoodInfoDto goodInfoDto,Integer userId); //成功返回1,如果商品的userId和userId不一致返回-1
    Integer removeGoods(Integer goodsId,Integer userId);  //成功返回1，如果goods的userId和goodsId不相同，返回-1
    void changeState(Integer state,Integer goodsId);
    Integer getMaxGoodsPage(Integer category, Integer attrition);
}
