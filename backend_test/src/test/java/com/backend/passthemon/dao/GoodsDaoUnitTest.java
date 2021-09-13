package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GoodsDaoUnitTest {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    UserDao userDao;

    private List<Goods> goodsList = new ArrayList<>();

    private Pageable pageable = PageRequest.of(0, 8);

    @Test
    public void findGoodsById() {
        Goods goods=new Goods();
        Goods goods1=goodsDao.save(goods);
        Assert.assertEquals(goods1,goodsDao.findGoodsById(goods1.getId()));
    }

    @Test
    public void findAllByState() {
        Goods goods = new Goods();
        goods.setState(10);
        goodsDao.save(goods);
        goodsList.add(goods);
        Assert.assertEquals(goodsList, goodsDao.findAllByState(10, pageable).getContent());
    }

    @Test
    public void findAllByNameLikeAndState() {
        String search = "search";
        Goods goods = new Goods();
        goods.setState(1);
        goods.setName(search);
        goodsDao.save(goods);
        goodsList.add(goods);
        Assert.assertEquals(goodsList, goodsDao.findAllByNameLikeAndState(search, pageable, 1).getContent());
    }

    @Test
    public void findAllByUserAndStateIn() {
        List<Integer> stateList = new ArrayList<>();
        stateList.add(10);
        Goods goods = new Goods();
        User user = new User();
        goods.setState(10);
        goods.setUser(user);
        userDao.save(user);
        goodsDao.save(goods);
        goodsList.add(goods);
        Assert.assertEquals(goodsList, goodsDao.findAllByUserAndStateIn(pageable, user, stateList).getContent());
    }

    @Test
    public void findAllByStateAndAttritionAndCategory() {
        Goods goods = new Goods();
        goods.setState(10);
        goods.setCategory(10);
        goods.setAttrition(-10);
        goodsDao.save(goods);
        goodsList.add(goods);
        Assert.assertEquals(goodsList, goodsDao.findAllByStateAndAttritionAndCategory(pageable, 10, -10, 10).getContent());
    }

    @Test
    public void findAllByStateAndAttrition() {
        Goods goods = new Goods();
        goods.setState(10);
        goods.setAttrition(-10);
        goodsDao.save(goods);
        goodsList.add(goods);
        Assert.assertEquals(goodsList, goodsDao.findAllByStateAndAttrition(pageable, 10, -10).getContent());
    }

    @Test
    public void findAllByStateAndCategory() {
        Goods goods = new Goods();
        goods.setState(10);
        goods.setCategory(10);
        goodsDao.save(goods);
        goodsList.add(goods);
        Assert.assertEquals(goodsList, goodsDao.findAllByStateAndCategory(10, 10, pageable).getContent());
    }
}
