package com.backend.passthemon.service;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;

import java.util.List;

public interface WantsService {
    Integer addWants(Integer buyerId, Integer sellerId, Integer goodsId, Integer num);
    Integer deleteWants(Integer buyerId, Integer sellerId, Integer goodsId);
    List<Wants> sellerListWants(Integer sellerId, Integer fetchPage);
    Wants checkWants(Integer userId, Integer goodsId);
}
