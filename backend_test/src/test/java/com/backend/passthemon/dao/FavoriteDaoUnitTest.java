package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteDaoUnitTest {
    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private UserDao userDao;

    List<Favorite> favoriteList = new ArrayList<>();

    Pageable pageable = PageRequest.of(0, 8);

    @Test
    void findAllFavoriteByUser() {
        Favorite favorite = new Favorite();
        User user = new User();
        Goods goods = new Goods();
        favorite.setUser(user);
        favorite.setGoods(goods);
        Favorite saveFavorite = favoriteDao.save(favorite);
        userDao.save(user);
        goodsDao.save(goods);
        favoriteList.add(saveFavorite);
        Assert.assertEquals(favoriteList, favoriteDao.findAllFavoriteByUser(user, pageable).getContent());
    }

    @Test
    void findFavoriteByGoodsAndUser() {
        Favorite favorite = new Favorite();
        User user = new User();
        Goods goods = new Goods();
        favorite.setUser(user);
        favorite.setGoods(goods);
        userDao.save(user);
        goodsDao.save(goods);
        Favorite saveFavorite = favoriteDao.save(favorite);
        Assert.assertEquals(saveFavorite, favoriteDao.findFavoriteByGoodsAndUser(goods, user));
    }

    @Test
    void deleteByGoodsAndUser(){
        Favorite favorite = new Favorite();
        User user = new User();
        Goods goods = new Goods();
        favorite.setUser(user);
        favorite.setGoods(goods);
        userDao.save(user);
        goodsDao.save(goods);
        favoriteDao.save(favorite);
        Integer expect = 1;
        Assert.assertEquals(expect, favoriteDao.deleteByGoodsAndUser(goods, user));
    }

    @Test
    void deleteByUser(){
        Favorite favorite = new Favorite();
        User user = new User();
        Goods goods = new Goods();
        favorite.setUser(user);
        favorite.setGoods(goods);
        userDao.save(user);
        goodsDao.save(goods);
        favoriteDao.save(favorite);
        Integer expect = 1;
        Assert.assertEquals(expect, favoriteDao.deleteByUser(user));
    }
}
