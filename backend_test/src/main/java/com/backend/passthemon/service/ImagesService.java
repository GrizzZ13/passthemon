package com.backend.passthemon.service;

import com.backend.passthemon.entity.Images;

import java.util.List;

public interface ImagesService {
    Integer addImg(String img, Integer goodsId, Integer displayOrder);
    List<Images> getAllImgByGoodsId(Integer goodsId);
}
