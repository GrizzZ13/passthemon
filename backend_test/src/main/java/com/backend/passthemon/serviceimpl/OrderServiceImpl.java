package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.constant.OrderConstant;
import com.backend.passthemon.dto.OrderDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.OrderRepository;
import com.backend.passthemon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    GoodsRepository goodsRepository;

    public Integer changeOrderStatus(Integer orderId, Integer status) {
        return orderRepository.changeOrderStatus(orderId, status);
    };

    public Integer makeOrder (User user, Integer goodsId, Integer goodsNum) {
        Goods goods = goodsRepository.findGoodsById(goodsId);
        if (goods == null) return OrderConstant.GOODS_NOT_FOUNT;
        else return orderRepository.makeOrder(user, goods, goodsNum);
    };

    public Integer affirmWants(Integer wantsId) {
        return orderRepository.affirmWants(wantsId);
    };

    public List<OrderDto> getOrdersByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage) {
        return orderRepository.getOrdersByUser(user, startTime, endTime, fetchPage);
    };

    public List<OrderDto> getOrdersAsBuyerByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage) {
        return orderRepository.getOrdersByBuyer(user, startTime, endTime, fetchPage);
    };

    public List<OrderDto> getOrdersAsSellerByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage) {
        return orderRepository.getOrdersBySeller(user, startTime, endTime, fetchPage);
    };

    public Integer commentAndRateOnOrder(Integer orderId, String comment, Integer rating) {
        return orderRepository.commentAndRateOnOrder(orderId, comment, rating);
    }

}
