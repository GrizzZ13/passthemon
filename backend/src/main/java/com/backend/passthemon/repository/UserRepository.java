package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;

import java.util.List;

public interface UserRepository {
    User findUserByIdWithCache(Integer userId);
    User findUserById(Integer userId);
    User getUserByEmail(String email);
    User save(User user);
    List<Follow> getFollowsByUserid(Integer userid);
    List<Follow> listFollowsByPage(Integer fetchPage, Integer userid);
    String getUserNameById(Integer userid);
    List<String> getAuthorityByUserid(Integer userid);
}
