package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Images;
import com.backend.passthemon.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImagesDao extends MongoRepository<Images, Integer> {
    Images findByGoodsIdAndAndDisplayOrder(Integer goods_id, Integer display_order);

    List<Images> findAllByGoodsId(Integer goodsId);

    @Modifying
    @Transactional
    Integer deleteAllByGoodsId(Integer goodsId);
}
