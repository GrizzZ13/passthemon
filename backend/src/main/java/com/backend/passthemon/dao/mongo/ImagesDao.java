package com.backend.passthemon.dao.mongo;

import com.backend.passthemon.entity.Images;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImagesDao extends MongoRepository<Images, Integer> {
    Images findImagesByGoodsId(Integer goodsId);

    @Modifying
    @Transactional
    void deleteImagesByGoodsId(Integer goodsId);
}
