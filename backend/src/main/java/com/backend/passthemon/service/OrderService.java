package com.backend.passthemon.service;

import com.backend.passthemon.dto.OrderDto;

import com.backend.passthemon.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    String test();
    Integer changeOrderStatus(Integer orderId, Integer status);
    Integer makeOrder (User user, Integer goodsId, Integer goodsNum);
    Integer affirmWants(Integer orderId);
    List<OrderDto> getOrdersByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage);
    List<OrderDto> getOrdersAsBuyerByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage);
    List<OrderDto> getOrdersAsSellerByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage);
    Integer commentAndRateOnOrder(Integer orderId, String comment, Integer rating,Integer userId);
}
