package com.backend.passthemon.service;

import com.backend.passthemon.entity.Images;

import java.util.List;

public interface ImagesService {
    Integer addImg(List<String> imgList, Integer goodsId);
    List<String> getAllImgByGoodsId(Integer goodsId);
    String getAllCoverForThisPage(Integer goodsId);
}
