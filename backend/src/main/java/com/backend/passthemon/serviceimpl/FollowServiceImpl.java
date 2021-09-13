package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.repository.FollowRepository;
import com.backend.passthemon.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowRepository followRepository;

    @Override
    public Follow addFollow(Follow follow){
        return followRepository.addFollow(follow);
    }

    @Override
    public  void unFollow(Integer followId){
        followRepository.unFollow(followId);
    }
}
