package com.backend.passthemon.dao;

 import com.backend.passthemon.dao.OrderDao;
 import com.backend.passthemon.dao.UserDao;
import com.backend.passthemon.entity.Order;
import com.backend.passthemon.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderDaoUnitTest {
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;

    @Test
    public void testSave(){
        User buyer = new User(); userDao.save(buyer);
        User seller = new User(); userDao.save(seller);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Order order = new Order(buyer, seller, timestamp);
        Assert.assertEquals(timestamp, orderDao.save(order).getTimestamp());
    }

}
