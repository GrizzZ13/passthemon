package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.constant.UserConstant;
import com.backend.passthemon.dto.UserInfoDto;
import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Verification;
import com.backend.passthemon.service.EmailService;
import com.backend.passthemon.service.FollowService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.service.VerificationService;
import com.backend.passthemon.utils.emailutils.EmailUtil;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.backend.passthemon.utils.userutils.UserInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
class UserControllerUintTest {
    @MockBean
    private UserService userService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private VerificationService verificationService;

    @MockBean
    private FollowService followService;

    @MockBean
    private Verification verification;

    @MockBean
    private RuntimeException runtimeException;

    @Autowired
    private MockMvc mvc;

    private final User user=new User();
    private List<Follow> followList=new ArrayList<>();
    private String username;
    private final Follow follow=new Follow();
    private List<UserInfoDto> userInfoDtoList=new ArrayList<>();

    @Test
    void login() {
        String email = "user", password = "user";
        given(this.userService.login(email, password)).willReturn(null);
        try {
            JSONObject jsonObject = new JSONObject();
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/user/login?email=" + email + "&password=" + password))
                    .andExpect(status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void sendEmail() {
        String email = "user", opt = "signup", verificationCode = "verificationCode";
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/sendEmail?email=" + email + "&opt=" + opt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        email = "391678792hd@sjtu.edu.cn";

        opt = "";
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/sendEmail?email=" + email + "&opt=" + opt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        opt = "signup";
        given(this.userService.getUserByEmail(email)).willReturn(user);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ACCOUNT_ALREADY_EXIST, MsgUtil.ACCOUNT_ALREADY_EXIST_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/sendEmail?email=" + email + "&opt=" + opt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        opt = "not_reset";
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/sendEmail?email=" + email + "&opt=" + opt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        opt = "reset";
        given(this.verificationService.getVerificationByEmail(email)).willReturn(verification);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/sendEmail?email=" + email + "&opt=" + opt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        opt = "reset";
        given(verificationService.getVerificationByEmail(email)).willThrow(runtimeException);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/sendEmail?email=" + email + "&opt=" + opt))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void signup() {
        String email = "user", password = "user", code = "666666";
        /* 测试账号已经存在 */
        given(this.userService.getUserByEmail(email)).willReturn(user);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ACCOUNT_ALREADY_EXIST, MsgUtil.ACCOUNT_ALREADY_EXIST_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/signup?email=" + email + "&password=" + password
            + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e){
            e.printStackTrace();
        }

        given(this.userService.getUserByEmail(email)).willReturn(null);
        given(this.verificationService.getVerificationByEmail(email)).willReturn(null);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/signup?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        given(this.verificationService.getVerificationByEmail(email)).willReturn(verification);
        given(this.verification.getCode()).willReturn(code);
        given(this.verification.getTime()).willReturn(System.currentTimeMillis() - 1);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.CODE_EXPIRED, MsgUtil.CODE_EXPIRED_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/signup?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        given(this.verification.getTime()).willReturn(System.currentTimeMillis() * 2);
        given(this.userService.signup(email, password)).willReturn(user);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/signup?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void reset() {
        String email = "user", password = "user", code = "666666";

        given(this.userService.getUserByEmail(email)).willReturn(null);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ACCOUNT_NOT_EXIST, MsgUtil.ACCOUNT_NOT_EXIST_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/reset?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        given(this.userService.getUserByEmail(email)).willReturn(user);
        given(this.verificationService.getVerificationByEmail(email)).willReturn(null);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ERROR, MsgUtil.ERROR_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/reset?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        given(this.verificationService.getVerificationByEmail(email)).willReturn(verification);
        given(this.verification.getCode()).willReturn(code);
        given(this.verification.getTime()).willReturn(System.currentTimeMillis() - 1);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.CODE_EXPIRED, MsgUtil.CODE_EXPIRED_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/reset?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        given(this.verification.getTime()).willReturn(System.currentTimeMillis() * 2);
        given(this.userService.reset(email, password)).willReturn(user);
        try {
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG);
            mvc.perform(MockMvcRequestBuilders.get("/user/reset?email=" + email + "&password=" + password
                    + "&code=" + code))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(msg.getStatus()))
                    .andExpect(jsonPath("$.message").value(msg.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void getUserByUserId() {
        Integer userid=1;
        given(this.userService.getUserByUserId(userid)).willReturn(user);
        try {
            JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSONString(user));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/user/getUserByUserId?userId=" + userid.toString() ))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    @Test
    void getUserInfoByUserId() {
        Integer userid=1;
        given(this.userService.getUserByUserId(userid)).willReturn(user);
        UserInfo userInfo=new UserInfo(user);
        try {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(userInfo));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/user/getUserInfoByUserId?userId=" + userid.toString() ))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getOtherInfo() {
        Integer userid = 1, myId = 2, followId = 0;

        given(this.userService.getUserByUserId(userid)).willReturn(user);
        UserInfo userInfo=new UserInfo(user);

        given(this.userService.getFollowsByUserid(myId)).willReturn(followList);

        followList.add(follow);
        for (Follow follow : followList) {
            follow.setFollower(userid);
            follow.setId(followId);
        };
        userInfo.setFollowId(followId);

        try{
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(userInfo));
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/user/getOtherInfo?otherId=" + userid.toString() +
                    "&userId=" + myId.toString()))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));

        } catch (Exception e){
            e.printStackTrace();
        };

    }

    @Test
    void getUserNameById() {
        Integer userid=1;
        given(this.userService.getUserNameById(userid)).willReturn(username);
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("username",username);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.get("/user/getUserNameById?userId=" + userid.toString()))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void editUser() {
        Integer userid=1;
        try{
            JSONObject requestBody=new JSONObject();
            requestBody.put("name","杰哥");
            requestBody.put("phone","13857050024");
            requestBody.put("userid",1);
            requestBody.put("gender",1);
            requestBody.put("credit",10);
            requestBody.put("image"," ");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("flag",true);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
            mvc.perform(MockMvcRequestBuilders.post("/user/submitChange").content(requestBody.toString()).contentType("application/json"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void followOther() {
     Integer userid=2;
     Integer myId=1;
     given(this.userService.getUserByUserId(myId)).willReturn(user);
     Follow myFollow=new Follow(userid,user);
     given(this.followService.addFollow(myFollow)).willReturn(follow);
     try{
         JSONObject jsonObject=new JSONObject();
         jsonObject.put("followId",follow.getId());
         Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
         mvc.perform(MockMvcRequestBuilders.get("/user/followOther?userId=" + userid.toString()+"&otherId=" + myId.toString()))
                 .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
     }catch (Exception e){
         e.printStackTrace();
     }
    }

    @Test
    void unFollowOther() {
        Mockito.doNothing().when(followService).unFollow(Mockito.anyInt());
        Integer followId=1;
        try{
            mvc.perform(MockMvcRequestBuilders.get("/user/unFollowOther?followId="+followId));
            Mockito.verify(followService,Mockito.times(1)).unFollow(followId);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getUsersByPage() {
       Integer userid=1;
       Integer fetchPage=1;
       given(this.userService.listFollowingsByPage(fetchPage,userid)).willReturn(userInfoDtoList);
       try{
           JSONObject jsonObject=new JSONObject();
           jsonObject.put("list",userInfoDtoList);
           Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);
           mvc.perform(MockMvcRequestBuilders.get("/user/getUsersByPage?fetchPage=" + fetchPage+"&userId=" + userid))
                   .andExpect(status().isOk());
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
