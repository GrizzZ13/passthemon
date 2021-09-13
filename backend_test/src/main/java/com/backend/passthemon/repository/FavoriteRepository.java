package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.utils.msgutils.Msg;

import java.util.List;

public interface FavoriteRepository {
    List<Favorite> listFavoriteByPage(Integer fetchPage, User user);
    Favorite addFavorite(User user, Goods goods);
    Favorite deleteFavorite(User user, Goods goods);
    Favorite deleteAllFavorite(User user);
    Favorite checkFavorite(User user, Goods goods);
}
