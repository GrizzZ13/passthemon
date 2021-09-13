package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Images;

import java.util.List;

public interface ImagesRepository {
    List<Images> getAllImgByGoodsId(Integer goodsId);
    Integer addImg(String img,Integer goodsId,Integer displayOrder);
}
