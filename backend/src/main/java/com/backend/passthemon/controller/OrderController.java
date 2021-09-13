package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.annotation.RequestConsistent;
import com.backend.passthemon.consumer.ImgCensor;
import com.backend.passthemon.consumer.TextCensor;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.OrderService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.backend.passthemon.constant.UserConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
public class OrderController {
    /* 仅允许引入Service */
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private ImgCensor imgCensor;
    @Autowired
    private TextCensor textCensor;

    @GetMapping(value = "/order/test")
    public String test (Integer who) {
        return imgCensor.ImgCensor(1, 1, who).toString();
    };

    @GetMapping("/order/changeOrderStatus")
    @RequestConsistent(identityConsistent = true)
    public Msg changeOrderStatus(@RequestParam("orderId") Integer orderId,
                                 @RequestParam("status") Integer status)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("returnVal", orderService.changeOrderStatus(orderId, status));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
    };

    @GetMapping("/order/makeOrder")
    @RequestConsistent(identityConsistent = true)
    public Msg makeOrder(@RequestParam("userId") Integer userId,
                         @RequestParam("goodsId") Integer goodsId,
                         @RequestParam("goodsNum") Integer goodsNum) {
        User user = userService.getUserByUserId(userId);  /* 获取用户信息 */
        Integer ans = user == null ? UserConstant.NOT_FOUND : orderService.makeOrder(user, goodsId, goodsNum);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("returnVal", ans);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
        return msg;
    }

    @GetMapping("/order/affirmWants")
    @RequestConsistent(identityConsistent = true)
    public Msg affirmWants(@RequestParam("wantsId") Integer wantsId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("returnVal", orderService.affirmWants(wantsId));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
    };

    @GetMapping("/order/getOrdersByUserId")
    public Msg getOrdersByUserId(@RequestParam("userId") Integer userId,
                                 @RequestParam(value = "fetchPage") Integer fetchPage,
                                 @RequestParam(value = "startTime", required = false) Timestamp startTime,
                                 @RequestParam(value = "endTime", required = false) Timestamp endTime) {
        User user = new User(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders", orderService.getOrdersByUser(user, startTime, endTime, fetchPage));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
    }

    @GetMapping("/order/getOrdersAsBuyerByUserid")
    @RequestConsistent(identityConsistent = true)
    public Msg getOrdersAsBuyerByUserid(@RequestParam("userId") Integer userId,
                                        @RequestParam(value = "fetchPage") Integer fetchPage,
                                        @RequestParam(value = "startTime", required = false) Timestamp startTime,
                                        @RequestParam(value = "endTime", required = false) Timestamp endTime)
    {
        User user = new User(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders", orderService.getOrdersAsBuyerByUser(user, startTime, endTime, fetchPage));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
    }

    @RequestConsistent(identityConsistent = true)
    @GetMapping("/order/getOrdersAsSellerByUserid")
    public Msg getOrdersAsSellerByUserid(@RequestParam("userId") Integer userId,
                                         @RequestParam(value = "fetchPage") Integer fetchPage,
                                         @RequestParam(value = "startTime", required = false) Timestamp startTime,
                                         @RequestParam(value = "endTime", required = false) Timestamp endTime)
    {
        User user = new User(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders", orderService.getOrdersAsSellerByUser(user, startTime, endTime, fetchPage));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
    }

    @GetMapping("/order/commentAndRateOnOrder")
    @RequestConsistent(identityConsistent = true)
    public Msg commentAndRateOnOrder(@RequestParam("orderId") Integer orderId,
                                     @RequestParam("comment") String comment,
                                     @RequestParam("rating") Integer rating,
                                     @RequestParam("userId") Integer userId)
    {
        JSONObject jsonObject = new JSONObject();
        Integer result=orderService.commentAndRateOnOrder(orderId, comment, rating,userId);
        if(result.equals(-1)){
            Msg msg = MsgUtil.makeMsg(MsgUtil.PERMISSION_DENIED,MsgUtil.PERMISSION_DENIED_MSG);
            return msg;
        }
        jsonObject.put("returnVal",result);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject, request);
    }
}
