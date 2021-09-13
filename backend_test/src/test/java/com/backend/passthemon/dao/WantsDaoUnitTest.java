package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WantsDaoUnitTest {
    @Autowired
    private WantsDao wantsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GoodsDao goodsDao;

    private List<Wants> wantsList = new ArrayList<>();

    private Pageable pageable = PageRequest.of(0, 8);

    @Test
    void findById() {
        Wants testWants = new Wants();
        User buyer = new User();
        User seller = new User();
        Goods goods = new Goods();
        goods.setUser(seller);
        testWants.setGoods(goods);
        testWants.setSeller(seller);
        testWants.setBuyer(buyer);
        userDao.save(buyer);
        userDao.save(seller);
        goodsDao.save(goods);
        Wants saveWants = wantsDao.save(testWants);
        Assert.assertEquals(saveWants, wantsDao.findById(saveWants.getId()));
    }

    @Test
    void deleteAllWantsByGoods() {
        Wants testWants = new Wants();
        User buyer = new User();
        User seller = new User();
        Goods goods = new Goods();
        goods.setUser(seller);
        testWants.setGoods(goods);
        testWants.setSeller(seller);
        testWants.setBuyer(buyer);
        wantsDao.save(testWants);
        userDao.save(buyer);
        userDao.save(seller);
        goodsDao.save(goods);
        Integer expect = 1;
        Assert.assertEquals(expect, wantsDao.deleteAllWantsByGoods(goods));
    }

    @Test
    void deleteWantsByBuyerAndGoodsAndSeller() {
        Wants testWants = new Wants();
        User buyer = new User();
        User seller = new User();
        Goods goods = new Goods();
        goods.setUser(seller);
        testWants.setGoods(goods);
        testWants.setSeller(seller);
        testWants.setBuyer(buyer);
        userDao.save(buyer);
        userDao.save(seller);
        goodsDao.save(goods);
        wantsDao.save(testWants);
        Integer expect = 1;
        Assert.assertEquals(expect, wantsDao.deleteWantsByBuyerAndGoodsAndSeller(buyer, goods, seller));
    }

    @Test
    void findAllBySeller() {
        Wants testWants = new Wants();
        User buyer = new User();
        User seller = new User();
        Goods goods = new Goods();
        goods.setUser(seller);
        testWants.setGoods(goods);
        testWants.setSeller(seller);
        testWants.setBuyer(buyer);
        userDao.save(buyer);
        userDao.save(seller);
        goodsDao.save(goods);
        Wants saveWants= wantsDao.save(testWants);
        wantsList.add(saveWants);
        Assert.assertEquals(wantsList, wantsDao.findAllBySeller(seller, pageable).getContent());
    }

    @Test
    void findWantsByBuyerAndGoods() {
        Wants testWants = new Wants();
        User buyer = new User();
        User seller = new User();
        Goods goods = new Goods();
        goods.setUser(seller);
        testWants.setGoods(goods);
        testWants.setSeller(seller);
        testWants.setBuyer(buyer);
        userDao.save(buyer);
        userDao.save(seller);
        goodsDao.save(goods);
        Wants saveWants= wantsDao.save(testWants);
        Assert.assertEquals(saveWants, wantsDao.findWantsByBuyerAndGoods(buyer, goods));
    }
}
