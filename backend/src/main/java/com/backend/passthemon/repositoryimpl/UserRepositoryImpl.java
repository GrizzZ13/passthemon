package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.AuthorityDao;
import com.backend.passthemon.dao.jpa.FollowDao;
import com.backend.passthemon.dao.jpa.UserDao;
import com.backend.passthemon.entity.Authority;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserDao userDao;

    @Autowired
    private FollowDao followDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    @Cacheable(value="user", key="'userId-'+#userId", unless="#result==null")
    public User findUserByIdWithCache(Integer userId){
        log.info("[User Repository] : find user with cache - database operation");
        return userDao.findUserById(userId);
    }

    @Override
    @CachePut(value="user", key="'userId-'+#userId", unless="#result==null")
    public User findUserById(Integer userId) {
        log.info("[User Repository] : find user - database operation");
        return userDao.findUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    @CachePut(value = "user", key="'userId-'+#result.id")
    public User save(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    public List<Follow> getFollowsByUserid(Integer userid){
        User user = new User(userid);
        return followDao.findAllByUser(user);
    }
    @Override
    public List<Follow> listFollowsByPage(Integer fetchPage, Integer userid){
        PageRequest pageRequest = PageRequest.of(fetchPage,8);
        Page<Follow> page = followDao.findAllByUser(new User(userid), pageRequest);
        return page.getContent();
    }
    @Override
    public String getUserNameById(Integer userid){
        User user=userDao.findUserById(userid);
        return user.getUsername();
    }
    @Override
    public List<String> getAuthorityByUserid(Integer userid){
        User user = new User(userid);
        List<Authority> authorities=authorityDao.findAllByUser(user);
        List<String> result=new ArrayList<>();
        for (Authority authority : authorities) {
            result.add(authority.getAuthorityName());
        }
        return result;
    }
}
