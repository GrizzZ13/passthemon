package com.backend.passthemon.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.constant.UserConstant;
import com.backend.passthemon.dto.UserInfoDto;
import com.backend.passthemon.entity.Authority;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.*;
import com.backend.passthemon.service.RedisService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.backend.passthemon.utils.tokenutils.JwtUtil;
import com.backend.passthemon.utils.userutils.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.backend.passthemon.constant.AuthorityConstant.*;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationRepository verificationRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    DemandRepository demandRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public User getUserByUserId(Integer userId) {
        return userRepository.findUserByIdWithCache(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Transactional
    @Override
    public Msg login(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if(user == null) {
            log.info("[UserService-Login] : " + MsgUtil.ACCOUNT_NOT_EXIST_MSG);
            return MsgUtil.makeMsg(MsgUtil.ACCOUNT_NOT_EXIST, MsgUtil.ACCOUNT_NOT_EXIST_MSG);
        }
        else if(!user.getPassword().equals(password)) {
            log.info("[UserService-Login] : " + MsgUtil.WRONG_PASSWORD_MSG);
            return MsgUtil.makeMsg(MsgUtil.WRONG_PASSWORD, MsgUtil.WRONG_PASSWORD_MSG);
        }
        else {
            String token = null;
            String refreshToken = null;
            try{
                token = JwtUtil.generateAccessJWT(email, user.getId());
                refreshToken = JwtUtil.generateRefreshJWT(email, user.getId());
            }
            catch (Exception e){
                e.printStackTrace();
                log.info("[UserService-Login] : " + MsgUtil.WRONG_PASSWORD_MSG);
                return MsgUtil.makeMsg(MsgUtil.WRONG_PASSWORD, MsgUtil.WRONG_PASSWORD_MSG);
            }
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
            log.info("[UserService-Login] : token : " + token);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(UserConstant.TOKEN, token);
            jsonObject.put(UserConstant.USERID, user.getId());
            return MsgUtil.makeMsg(MsgUtil.LOGIN_SUCCEED, MsgUtil.LOGIN_SUCCEED_MSG, jsonObject);
        }
    }

    @Transactional
    @Override
    public User signup(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setCredit(UserConstant.INI_CREDIT);
        user.setGender(UserConstant.INI_GENDER);
        user.setState(UserConstant.INI_STATE);
        List<Authority> authorityList=new ArrayList<>();
        user = userRepository.save(user);
        authorityList.add(new Authority(COMMON_USER, user));
        authorityRepository.addAuthority(authorityList);
        return user;
    }

    @Transactional
    @Override
    public User reset(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user==null)
            return null;
        else{
            user.setPassword(password);
            user = userRepository.save(user);
            return user;
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void editUser(Integer userid,String username,String phone,String image,Integer gender){
        User user=userRepository.findUserById(userid);
        user.setUsername(username);
        user.setPhone(phone);
        user.setImage(image);
        user.setGender(gender);
        userRepository.save(user);
    }
    @Override
    public List<Follow> getFollowsByUserid(Integer userid){
        return userRepository.getFollowsByUserid(userid);
    }

    @Override
    public  List<UserInfoDto> listFollowingsByPage(Integer fetchPage, Integer userid){
        List<Follow> follows = userRepository.listFollowsByPage(fetchPage,userid);
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        for (Follow follow : follows)
        {
            User user = userRepository.findUserByIdWithCache(follow.getFollower());
            if (user!=null)
            {
                userInfoDtoList.add(UserInfoDto.convert(user));
            }
        }
        return userInfoDtoList;
    }

    @Override
    public  String getUserNameById(Integer userid){
        return userRepository.findUserByIdWithCache(userid).getUsername();
    }

    @Override
    public UserInfo getUserInfoBuUserId(Integer userid) {
        User user = userRepository.findUserByIdWithCache(userid);
        UserInfo userInfo = UserInfo.getIncompleteUserInfo(user);
        userInfo.setFavoriteNum(favoriteRepository.countFavoritesByUser(user));
        userInfo.setBuyNum(orderRepository.countOrdersByBuyer(user));
        userInfo.setSellNum(orderRepository.countOrdersBySeller(user));
        userInfo.setGoodsNum(goodsRepository.countGoodsByUserAndStateIsNot(user, 0));
        userInfo.setDemandsNum(demandRepository.countDemandsByUserAndStateIsNot(user, 0));
        return userInfo;
    }
}
