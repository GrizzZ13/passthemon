package com.backend.passthemon.interceptor;

import com.alibaba.fastjson.JSON;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.backend.passthemon.utils.tokenutils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("[JwtInterceptor] : Jwt Interceptor pre-handler.");
        String jwt = getJwt(request);
        Integer status = JwtUtil.verify(jwt);
        if(status.equals(JwtUtil.TOKEN_INVALID)) {
            Msg msg = MsgUtil.makeMsg(MsgUtil.TOKEN_INVALID, MsgUtil.TOKEN_INVALID_MSG);
            sendJsonBack(response, msg);
            log.info("[JwtInterceptor] : JWT-AUTH-FAILED.");
            return false;
        }
        else if(status.equals(JwtUtil.TOKEN_EXPIRED)) {
            // access token update flag
            boolean updateFlag = false;

            // check refresh token
            String subject = JwtUtil.getSubject(jwt);
            Integer userid = JwtUtil.getUserid(jwt);
            if(userid != null){
                User user = userService.getUserByUserId(userid);
                if(user != null){
                    String refreshToken = user.getRefreshToken();
                    Integer refreshStatus = JwtUtil.verify(refreshToken);
                    if(refreshStatus.equals(JwtUtil.TOKEN_VERIFIED)){
                        updateFlag = true;
                    }
                }
            }
            Msg msg;
            if(updateFlag){
                String token = JwtUtil.generateAccessJWT(subject, userid);
                log.info("[JwtInterceptor] : JWT-AUTH-UPDATE.");
                msg = MsgUtil.makeMsg(MsgUtil.TOKEN_UPDATE, MsgUtil.TOKEN_UPDATE_MSG, token);
                sendJsonBack(response, msg);
                return false;
            }
            else {
                msg = MsgUtil.makeMsg(MsgUtil.TOKEN_EXPIRED, MsgUtil.TOKEN_EXPIRED_MSG);
                log.info("[JwtInterceptor] : JWT-AUTH-EXPIRED.");
                sendJsonBack(response, msg);
                return false;
            }
        }
        else if(status.equals(JwtUtil.TOKEN_VERIFIED)) {
            log.info("[JwtInterceptor] : JWT-AUTH-PASSED.");
            return true;
        }
        else throw new Exception();
    }

    private String getJwt(HttpServletRequest request) {
        return request.getHeader("Token");
    }

    private void sendJsonBack(HttpServletResponse response, Msg msg){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            String jsonBack = JSON.toJSONString(msg);
            log.info("[JwtInterceptor] : " + jsonBack);
            writer.print(jsonBack);
        } catch (IOException e) {
            System.out.println("[JwtInterceptor] : send-json-back error.");
        }
    }
}
