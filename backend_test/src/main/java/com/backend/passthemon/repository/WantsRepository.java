package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;

import java.util.List;

public interface WantsRepository {
    Integer addWants(User buyer, User seller, Goods goods, Integer num);
    Integer deleteWants(User buyer, User seller, Goods goods);
    List<Wants> sellerListWants(User seller, Integer fetchPage);
    Wants checkWants(User user, Goods goods);
}
