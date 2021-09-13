package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.AuthorityDao;
import com.backend.passthemon.dao.FollowDao;
import com.backend.passthemon.dao.UserDao;
import com.backend.passthemon.entity.Authority;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.redis.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private RedisService redisService;

    @Override
    @Cacheable(value="user", key="'userId-'+#userId", unless="#result==null")
    public User findUserById(Integer userId){
        log.info("[User Repository] : get user - database operation");
        return userDao.findUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        user = userDao.save(user);
        String key = KeyUtil.UserIdKey(user.getId());
        redisService.set(key, user);
        return user;
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
