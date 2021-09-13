package com.backend.passthemon.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.constant.UserConstant;
import com.backend.passthemon.dto.UserInfoDto;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.repository.VerificationRepository;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.backend.passthemon.utils.tokenutils.JwtUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class UserServiceImplUnitTest{
    @Mock
    UserRepository userRepository;

    @Mock
    VerificationRepository verificationRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final Integer userid = 1;
    private final Integer fetchPage = 1;
    private final User user = new User(userid);
    private String email = "sonoso@sjtu.edu.cn";
    private final String password = "666666";
    private final String username = "孤独的根号三";
    private final String phone = "13857050024";
    private final String image = "image";
    private String token = "token";
    private String refreshToken = "refreshToken";
    private final Integer gender = 1;
    private List<Follow> followList = new ArrayList<>();
    private Follow follow = new Follow();
    private List<User> userList = new ArrayList<>();
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(userRepository.findUserById(userid)).thenReturn(user);
        Mockito.when(userRepository.findUserById(~userid)).thenReturn(null);
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(user);
        Mockito.when(userRepository.getUserNameById(userid)).thenReturn(username);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.getFollowsByUserid(userid)).thenReturn(followList);
        Mockito.when(userRepository.listFollowsByPage(fetchPage, userid)).thenReturn(followList);
        followList.add(follow); follow.setFollower(userid);
    }
    @Test
    public void getUserByUserId() {
        User ret=userService.getUserByUserId(userid);
        Assert.assertEquals(ret,user);
        Mockito.verify(userRepository).findUserById(userid);
    }

    @Test
    public void getUserByEmail() {
        User ret=userService.getUserByEmail(email);
        Assert.assertEquals(ret,user);
        Mockito.verify(userRepository).getUserByEmail(email);
    }

    @Test
    public void login() {
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(null);
        Msg msg = MsgUtil.makeMsg(MsgUtil.ACCOUNT_NOT_EXIST, MsgUtil.ACCOUNT_NOT_EXIST_MSG);
        Assert.assertEquals(userService.login(email, password).toString(), msg.toString());
        Mockito.verify(userRepository).getUserByEmail(email);

        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(user);
        user.setPassword(password + "password");
        msg = MsgUtil.makeMsg(MsgUtil.WRONG_PASSWORD, MsgUtil.WRONG_PASSWORD_MSG);
        Assert.assertEquals(userService.login(email, password).toString(), msg.toString());

        user.setPassword(password);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UserConstant.TOKEN, token);
        jsonObject.put(UserConstant.USERID, userid);
        msg = MsgUtil.makeMsg(MsgUtil.LOGIN_SUCCEED, MsgUtil.LOGIN_SUCCEED_MSG, jsonObject);
        Assert.assertEquals(userService.login(email, password).getMessage(), msg.getMessage());
        Mockito.verify(userRepository).save(user);

        email = null;
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(user);
        msg = MsgUtil.makeMsg(MsgUtil.WRONG_PASSWORD, MsgUtil.WRONG_PASSWORD_MSG);
        Assert.assertEquals(userService.login(email, password).toString(), msg.toString());
    }

    @Test
    public void signup() {
        userService.signup(email, password);
        user.setId(null);
        user.setEmail(email);
        user.setPassword(password);
        user.setCredit(UserConstant.INI_CREDIT);
        user.setGender(UserConstant.INI_GENDER);
        user.setState(UserConstant.INI_STATE);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void reset() {
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(null);
        Assert.assertNull(userService.reset(email, password));
        Mockito.verify(userRepository).getUserByEmail(email);
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(user);
        Assert.assertEquals(userService.reset(email, password).getPassword(), user.getPassword());
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void save() {
        User ret=userService.save(user);
        Assert.assertEquals(ret,user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void editUser() {
        userService.editUser(userid, username, phone, image, gender);
        Mockito.verify(userRepository).findUserById(userid);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void getFollowsByUserid() {
        List<Follow> ret=userService.getFollowsByUserid(userid);
        Assert.assertEquals(ret,followList);
        Mockito.verify(userRepository).getFollowsByUserid(userid);
    }

    @Test
    public void listFollowingsByPage() {
        List<UserInfoDto> ret = userService.listFollowingsByPage(fetchPage, userid);
        userList.add(user);
        List<UserInfoDto> userInfoDtoList=UserInfoDto.convert(userList);
        Assert.assertEquals(ret, userInfoDtoList);
        Mockito.verify(userRepository).listFollowsByPage(fetchPage, userid);
    }

    @Test
    public void getUserNameById() {
        String ret = userService.getUserNameById(userid);
        Assert.assertEquals(ret, user.getUsername());
        Mockito.verify(userRepository).findUserById(userid);
    }
}
