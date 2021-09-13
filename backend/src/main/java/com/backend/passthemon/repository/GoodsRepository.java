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
    Goods addGood(Goods goods);
    Integer editGood(GoodInfoDto goodInfoDto,Integer userId);
    Integer removeGoods(Integer goodsId,Integer userId);
    void changeState(Integer state,Integer goodsId);
    Integer getMaxGoodsPage(Integer category, Integer attrition);
    Integer countGoodsByUserAndStateIsNot(User user, Integer state);
}
