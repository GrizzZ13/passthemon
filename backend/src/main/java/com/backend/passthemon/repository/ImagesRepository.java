package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Images;

import java.util.List;

public interface ImagesRepository {
    List<String> getAllImgByGoodsId(Integer goodsId);
    Integer addImg(List<String> imgList, Integer goodsId);
    String getAllCoverForThisPage(Integer goodsId);
}
