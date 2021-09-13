package com.backend.passthemon.dao;

import com.backend.passthemon.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoUnitTest {
    @Autowired
    UserDao userDao;
    @Test
    @Transactional
    @Rollback(true)
    void findUserById() {
        User user=new User();
        user.setGender(1);
        user.setPhone("13857050024");
        user.setUsername("孤独的根号三");
        user.setEmail("sonoso@sjtu.edu.cn");
        User ret=userDao.save(user);
        User ret2=userDao.findUserById(ret.getId());
        Assert.assertEquals(ret,ret2);
    }

//    @Test
//    @Transactional
//    @Rollback(true)
//    void getUserByEmail() {
//        User user=new User();
//        user.setGender(1);
//        user.setPhone("13857050024");
//        user.setUsername("孤独的根号三");
//        user.setEmail("sonoso@sjtu.edu.cn");
//        User ret=userDao.saveAndFlush(user);
//        User ret2=userDao.getUserByEmail(ret.getEmail());
//        Assert.assertEquals(ret,ret2);
//    }
}
