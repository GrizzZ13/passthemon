package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.FollowDao;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FollowRepositoryImpl implements FollowRepository {
    @Autowired
    FollowDao followDao;

    @Override
    public Follow addFollow(Follow follow){
        return followDao.saveAndFlush(follow);
    }
    @Override
    public  void unFollow(Integer followId){
        Follow follow=followDao.getOne(followId);
        followDao.delete(follow);
    }
}
