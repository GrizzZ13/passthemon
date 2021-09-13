package com.backend.passthemon.repository;

import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoodsRepository {
    Page<Goods> listGoodsByPages(Integer fetchPage, Integer category, Integer attrition);
    List<Goods> listMyGoodsByPages(Integer fetchPage, User user);
    List<Goods> searchGoods(String search, Integer fetchPage);
    Goods findGoodsById(Integer goodsId);
    Integer addGood(Goods goods);
    void editGood(GoodInfoDto goodInfoDto);
    void removeGoods(Integer goodsId);
    void changeState(Integer state,Integer goodsId);
    Integer getMaxGoodsPage(Integer category, Integer attrition);
}
