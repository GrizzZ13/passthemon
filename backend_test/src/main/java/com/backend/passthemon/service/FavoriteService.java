package com.backend.passthemon.service;

import com.backend.passthemon.dto.FavoriteDto;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.utils.msgutils.Msg;

import java.util.List;

public interface FavoriteService {
    List<FavoriteDto> listFavoriteByPage(Integer fetchPage, Integer userId);
    Favorite addFavorite(Integer userId, Integer goodsId);
    Favorite deleteFavorite(Integer userId, Integer goodsId);
    Favorite deleteAllFavorite(Integer userId);
    Favorite checkFavorite(Integer userId, Integer goodsId);
}
