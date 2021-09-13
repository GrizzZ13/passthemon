package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface WantsDao extends JpaRepository<Wants, Long> {
    Wants findById(Integer id);
    @Modifying
    @Transactional
    Integer deleteAllWantsByGoods(Goods goods);

    @Modifying
    @Transactional
    Integer deleteWantsByBuyerAndGoodsAndSeller(User buyer, Goods goods, User seller);

    Page<Wants> findAllBySeller(User seller, Pageable pageable);
    Wants findWantsByBuyerAndGoods(User buyer, Goods goods);
}
