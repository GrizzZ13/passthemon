package com.backend.passthemon.repository;

import com.backend.passthemon.dto.OrderDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Order;
import com.backend.passthemon.entity.User;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository {
    Integer changeOrderStatus(Integer orderId, Integer status);
    Integer makeOrder(User user, Goods goods, Integer goodsNum);
    Integer affirmWants(Integer wantsId);
    List<OrderDto> getOrdersByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage);
    List<OrderDto> getOrdersBySeller(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage);
    List<OrderDto> getOrdersByBuyer(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage);
    Integer commentAndRateOnOrder(Integer orderId, String comment, Integer rating);
}
