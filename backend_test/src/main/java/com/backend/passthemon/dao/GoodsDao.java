package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods, Integer> {
    Goods findGoodsById(int id);
    Page<Goods> findAllByState(Integer state, Pageable pageable);
    Page<Goods> findAllByNameLikeAndState(String search, Pageable pageable, Integer state);
    Page<Goods> findAllByUserAndStateIn(Pageable pageable, User user, List<Integer> state);
    Page<Goods> findAllByStateAndAttritionAndCategory(Pageable pageable, Integer state, Integer attrition, Integer category);
    Page<Goods> findAllByStateAndAttrition(Pageable pageable, Integer state, Integer attrition);
    Page<Goods> findAllByStateAndCategory(Integer state, Integer category, Pageable pageable);
}
