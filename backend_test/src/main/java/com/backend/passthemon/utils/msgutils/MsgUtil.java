package com.backend.passthemon.utils.msgutils;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.utils.tokenutils.JwtUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class MsgUtil {
    public static final Integer TOKEN_VERIFIED = 1;
    public static final Integer ALL_OK = 1;
    public static final Integer TOKEN_UPDATE = 20;
    public static final Integer LOGIN_SUCCEED = 1;

    public static final Integer TOKEN_INVALID = -10;
    public static final Integer TOKEN_EXPIRED = -20;
    public static final Integer WRONG_PASSWORD = -30;
    public static final Integer ACCOUNT_NOT_EXIST = -40;
    public static final Integer ACCOUNT_ALREADY_EXIST = -50;
    public static final Integer CODE_EXPIRED = -60;
    public static final Integer ERROR = -100;
    public static final Integer PERMISSION_DENIED=-70;
    public static final Integer VISITING_TOO_OFTEN=-80;

    public static final String TOKEN_VERIFIED_MSG = "token verification passed";
    public static final String TOKEN_EXPIRED_MSG = "Authentication expired, please login again!";
    public static final String TOKEN_INVALID_MSG = "Invalid authentication!";
    public static final String TOKEN_UPDATE_MSG = "Access token will update";

    public static final String LOGIN_SUCCEED_MSG = "login successfully";
    public static final String WRONG_PASSWORD_MSG = "wrong email or password";
    public static final String ACCOUNT_NOT_EXIST_MSG = "Account does not exist!";
    public static final String ACCOUNT_ALREADY_EXIST_MSG = "Account has already existed!";
    public static final String ALL_OK_MSG = "All OK!";
    public static final String ERROR_MSG = "Error!";
    public static final String CODE_EXPIRED_MSG = "Verification Code Expired!";
    public static final String PERMISSION_DENIED_MSG="Your permissions are insufficient";
    public static final String VISITING_TOO_OFTEN_MSG="You're visiting too often";


    public static Msg makeMsg(Integer status, String msg, JSONObject data){
        return Msg.builder()
                .status(status)
                .message(msg)
                .data(data)
                .build();
    }

    public static Msg makeMsg(Integer status, String msg, String token, JSONObject data){
        return Msg.builder()
                .status(status)
                .message(msg)
                .token(token)
                .data(data)
                .build();
    }

    public static Msg makeMsg(Integer status, String msg){
        return Msg.builder()
                .status(status)
                .message(msg)
                .build();
    }

    public static Msg makeMsg(Integer status, String msg, JSONObject jsonObject, HttpServletRequest httpServletRequest) {
        Msg retMsg = Msg.builder()
                .status(status)
                .message(msg)
                .data(jsonObject)
                .build();
//        String update = (String) httpServletRequest.getServletContext().getAttribute("update");
//        log.info("[MsgUtil.makeMsg] : " + update);
//        if( update != null && update.equals("true")) {
//            String subject = httpServletRequest.getHeader("subject");
//            try{
//                retMsg.setToken(JwtUtil.generateAccessJWT(subject));
//                retMsg.setStatus(MsgUtil.TOKEN_UPDATE);
//                retMsg.setMessage(MsgUtil.TOKEN_UPDATE_MSG);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//                retMsg.setStatus(MsgUtil.TOKEN_INVALID);
//                retMsg.setMessage(MsgUtil.TOKEN_INVALID_MSG);
//            }
//        }
        return retMsg;
    }

    public static Msg makeMsg(Integer status, String msg, String token){
        return Msg.builder()
                .status(status)
                .message(msg)
                .token(token)
                .build();
    }
}
