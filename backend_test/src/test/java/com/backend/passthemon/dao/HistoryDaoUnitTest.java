package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HistoryDaoUnitTest {
    @Autowired
    HistoryDao historyDao;

    @Autowired
    UserDao userDao;

    @Autowired
    GoodsDao goodsDao;

    private Pageable pageable = PageRequest.of(0, 15, Sort.Direction.DESC, "time");

    private List<History> historyList = new ArrayList<>();

    @Test
    void findAllHistoryByUser() {
        User testUser = new User();
        Goods testGoods = new Goods();
        User saveUser = userDao.save(testUser);
        Goods saveGoods = goodsDao.save(testGoods);
        History testHistory = new History();
        testHistory.setGoods(saveGoods);
        testHistory.setUser(saveUser);
        historyDao.save(testHistory);
        historyList.add(testHistory);
        Assert.assertEquals(historyList, historyDao.findAllHistoryByUser(saveUser, pageable).getContent());
    }

    @Test
    void findAllHistoryByUserAndTimeBetween() {
        User testUser = new User();
        Goods testGoods = new Goods();
        User saveUser = userDao.save(testUser);
        Goods saveGoods = goodsDao.save(testGoods);
        History testHistory = new History();
        testHistory.setGoods(saveGoods);
        testHistory.setUser(saveUser);
        History saveHistory= historyDao.save(testHistory);
        historyList.add(saveHistory);
        Timestamp startTime = Timestamp.valueOf("2021-01-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2031-01-01 00:00:00");
        Assert.assertEquals(historyList, historyDao.findAllHistoryByUserAndTimeBetween(saveUser, pageable, startTime, endTime).getContent());
    }

    @Test
    void findHistoryByUserAndGoods() {
        User testUser = new User();
        Goods testGoods = new Goods();
        User saveUser = userDao.save(testUser);
        Goods saveGoods = goodsDao.save(testGoods);
        History testHistory = new History();
        testHistory.setGoods(saveGoods);
        testHistory.setUser(saveUser);
        historyDao.save(testHistory);
        Assert.assertEquals(testHistory, historyDao.findHistoryByUserAndGoods(saveUser, saveGoods));
    }

    @Test
    void deleteAllByUser() {
        User testUser = new User();
        Goods testGoods = new Goods();
        User saveUser = userDao.save(testUser);
        Goods saveGoods = goodsDao.save(testGoods);
        History testHistory = new History();
        testHistory.setGoods(saveGoods);
        testHistory.setUser(saveUser);
        historyDao.save(testHistory);
        historyDao.deleteAllByUser(saveUser);
        List<History> expected = new ArrayList<>();
        Assert.assertEquals(expected, historyDao.findAllHistoryByUser(saveUser, pageable).getContent());
    }

    @Test
    void save(){
        History testHistory = new History();
        Assert.assertEquals(testHistory, historyDao.save(testHistory));
    }
}
