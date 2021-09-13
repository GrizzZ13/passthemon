package com.backend.passthemon.service;

import com.backend.passthemon.entity.Follow;

public interface FollowService {
    Follow addFollow(Follow follow);
    void unFollow(Integer followId);
}
