package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Demand;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemandDaoUnitTest {
    @Autowired
    DemandDao demandDao;

    @Autowired
    UserDao userDao;

    private Pageable pageable = PageRequest.of(0, 8, Sort.Direction.DESC, "upLoadTime");

    private List<Demand> demandList = new ArrayList<>();

    @Test
    void findDemandById() {
        Demand testDemand = new Demand();
        testDemand.setState(10);
        demandList.add(testDemand);
        Demand saveDemand = demandDao.save(testDemand);
        Assert.assertEquals(saveDemand, demandDao.findDemandById(saveDemand.getId()));
    }

    @Test
    void findAllByUserAndStateIn() {
        List<Integer> stateList = new ArrayList<>();
        stateList.add(1);
        Demand testDemand = new Demand();
        User testUser = new User();
        User saveUser = userDao.save(testUser);
        testDemand.setState(1);
        testDemand.setUser(saveUser);
        Demand saveDemand = demandDao.save(testDemand);
        demandList.add(saveDemand);
        Assert.assertEquals(demandList, demandDao.findAllByUserAndStateIn(pageable, saveUser, stateList).getContent());
    }

    @Test
    void findAllByState() {
        Demand testDemand = new Demand();
        testDemand.setState(10);
        demandList.add(testDemand);
        demandDao.save(testDemand);
        Assert.assertEquals(demandList, demandDao.findAllByState(10, pageable).getContent());
    }

    @Test
    void findAllByStateAndAttrition() {
        Demand testDemand = new Demand();
        testDemand.setState(10);
        testDemand.setAttrition(-10);
        demandDao.save(testDemand);
        demandList.add(testDemand);
        Assert.assertEquals(demandList, demandDao.findAllByStateAndAttrition(10, -10, pageable).getContent());
    }

    @Test
    void findAllByStateAndCategory(){
        Demand testDemand = new Demand();
        testDemand.setState(10);
        testDemand.setCategory(10);
        demandList.add(testDemand);
        demandDao.save(testDemand);
        Assert.assertEquals(demandList, demandDao.findAllByStateAndCategory(10, 10, pageable).getContent());
    }

    @Test
    void findAllByStateAndAttritionAndCategory() {
        Demand testDemand = new Demand();
        testDemand.setState(10);
        testDemand.setCategory(10);
        testDemand.setAttrition(-10);
        demandList.add(testDemand);
        demandDao.save(testDemand);
        Assert.assertEquals(demandList, demandDao.findAllByStateAndAttritionAndCategory(10, -10, 10, pageable).getContent());
    }
}
