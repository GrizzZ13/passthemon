package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Follow;

import java.util.List;

public interface FollowRepository {
    Follow addFollow(Follow follow);
    void unFollow(Integer followId);
}
