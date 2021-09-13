package com.backend.passthemon.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.annotation.AccessLimit;
import com.backend.passthemon.annotation.RequestAuthority;
import com.backend.passthemon.annotation.RequestConsistent;
import com.backend.passthemon.annotation.RequestLimit;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.RedisService;
import com.backend.passthemon.utils.authorityutils.AuthorityUtil;
import com.backend.passthemon.utils.iputils.IpAddressUtil;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import com.backend.passthemon.utils.tokenutils.JwtUtil;
import com.backend.passthemon.wrapper.RequestWrapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//该拦截器同时实现限流以及权限认证
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    //不同的方法存放不同的令牌桶
    private final Map<String, RateLimiter> rateLimiterMap=new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String jwt = getJwt(request);
        Integer userid= JwtUtil.getUserid(jwt);
        List<String> userAuthority=userRepository.getAuthorityByUserid(userid);
        Msg msg;
        if(!isAuthority(handler,userAuthority,userid,request)){
            msg = MsgUtil.makeMsg(MsgUtil.PERMISSION_DENIED,MsgUtil.PERMISSION_DENIED_MSG);
            sendJsonBack(response,msg);
            return false;
        }
        else{
            if(!isRequestLimit(request,handler)){
                msg = MsgUtil.makeMsg(MsgUtil.REQUEST_EXCESSIVE, MsgUtil.REQUEST_EXCESSIVE_MSG);
                sendJsonBack(response, msg);
                return false;
            }else {
                if (!isLimiting(request, handler)) {
                    msg = MsgUtil.makeMsg(MsgUtil.VISITING_TOO_OFTEN, MsgUtil.VISITING_TOO_OFTEN_MSG);
                    sendJsonBack(response, msg);
                    return false;
                }
                return true;
            }
        }
    }

    //判断此次访问是否有权限,第一个参数为访问的api,第二个参数为用户的权限,第三个参数为用户userid，第四个参数为请求。
    private boolean isAuthority(Object handler, List<String> userAuthority,Integer userid,HttpServletRequest request) {
        boolean flag = true;
        if (handler instanceof HandlerMethod) {
            //获得请求的方法
            Method method = ((HandlerMethod) handler).getMethod();
            //获得该方法上面的注解，如果没有注解，直接返回true,通过
            RequestAuthority annotation = method.getAnnotation(RequestAuthority.class);
            RequestConsistent annotation2 = method.getAnnotation(RequestConsistent.class);
            if(annotation2 != null){
                //判断身份与接口是否一致
                boolean shouldConsistent =annotation2.identityConsistent();
                //若需要一致
                if(shouldConsistent){
                    //判断请求类型
                    String MethodName=request.getMethod();
                    if(MethodName.equals("GET")){
                        String tmp=request.getParameter("goodsId");
                        String stringUserid=request.getParameter("userId");
                        Integer requestUserid = Integer.parseInt(request.getParameter("userId"));
                        if(!requestUserid.equals(userid)) return false;
                    }else if(MethodName.equals("POST")){
                        try{
                            String requestBody = new RequestWrapper(request).getBodyString();
                            JSONObject jsonObject=JSONObject.parseObject(requestBody);
                            Integer requestUserid=jsonObject.getInteger("userId");
                            if(!requestUserid.equals(userid)) return false;

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (annotation != null) {
                //获得注解的值（权限）
                String[] values = annotation.value();
                //是否所有的权限都满足才可以
                boolean andAuthority = annotation.andAuthority();
                if (andAuthority) {//该请求所有的权限都满足才可以
//                    flag = containsCheck(Arrays.asList(values), authorlist);
                    flag = AuthorityUtil.containCheck(Arrays.asList(values),userAuthority);
                } else {
                    flag = AuthorityUtil.haveCheck(Arrays.asList(values),userAuthority);
                }
            }
        }
        return flag;
    }
    //判断此次访问是否达到访问限制，针对个人访问限流
    private boolean isLimiting(HttpServletRequest request,Object handler){
        Msg msg;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimit.class)) {
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int limit = accessLimit.limit();
            Long sec= (long) (accessLimit.seconds());
            String key = IpAddressUtil.getIpAddress(request) + request.getRequestURI();
            Integer maxLimit = (Integer) redisService.get(key);
            if (maxLimit == null) {
                //set时一定要加过期时间
                redisService.set(key, 1, sec, TimeUnit.SECONDS);
            } else if (maxLimit < limit) {
                redisService.set(key, maxLimit + 1, sec, TimeUnit.SECONDS);
            } else {
                return false;
            }
        }
        return true;
    }
    private boolean isRequestLimit(HttpServletRequest request,Object handler){
        try {
            if(handler instanceof HandlerMethod){
                HandlerMethod handlerMethod=(HandlerMethod)handler;
                RequestLimit requestLimit=handlerMethod.getMethodAnnotation(RequestLimit.class);
                if(requestLimit != null){
                    //获取请求url
                    String url = request.getRequestURI();
                    RateLimiter rateLimiter;
                    //判断map集合中是否已经有创建好的令牌桶
                    if(!rateLimiterMap.containsKey(url)){
                        //创建令牌桶，加令牌
                        rateLimiter=RateLimiter.create(requestLimit.QPS());
                        rateLimiterMap.put(url,rateLimiter);
                    }
                    rateLimiter=rateLimiterMap.get(url);
                    //获取令牌
                    boolean acquire = rateLimiter.tryAcquire(requestLimit.timeout(),requestLimit.timeunit());
                    if(acquire){
                        return true;
                    } else {
                        return false;
                    }
                }
                return true;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    private String getJwt(HttpServletRequest request) {
        return request.getHeader("Token");
    }

    private void sendJsonBack(HttpServletResponse response, Msg msg){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            String jsonBack = JSON.toJSONString(msg);
            log.info("[Security Interceptor] : " + jsonBack);
            writer.print(jsonBack);
        } catch (IOException e) {
            System.out.println("[Security Interceptor] : send-json-back error.");
        }
    }
}
