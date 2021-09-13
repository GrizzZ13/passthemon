package com.backend.passthemon.service;

import com.backend.passthemon.dto.UserInfoDto;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;

import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.userutils.UserInfo;

import java.util.List;

public interface UserService {
    User getUserByUserId(Integer userId);
    User getUserByEmail(String email);
    Msg login(String email, String password);
    User signup(String email, String password);
    User reset(String email, String password);
    User save(User user);
    void editUser(Integer userid,String username,String phone,String image,Integer gender);
    List<Follow> getFollowsByUserid(Integer userid);
    List<UserInfoDto> listFollowingsByPage(Integer fetchPage, Integer userid);
    String getUserNameById(Integer userid);
    UserInfo getUserInfoBuUserId(Integer userid);
}
