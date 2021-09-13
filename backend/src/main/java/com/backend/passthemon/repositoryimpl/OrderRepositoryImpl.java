package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.constant.GoodsConstant;
import com.backend.passthemon.constant.OrderConstant;
import com.backend.passthemon.dao.jpa.GoodsDao;
import com.backend.passthemon.dao.jpa.UserDao;
import com.backend.passthemon.dao.mongo.ImagesDao;
import com.backend.passthemon.dao.jpa.OrderDao;
import com.backend.passthemon.dao.jpa.WantsDao;
import com.backend.passthemon.dto.OrderDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Order;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    OrderDao orderDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    WantsDao wantsDao;
    @Autowired
    ImagesDao imagesDao;
    @Autowired
    UserDao userDao;

    Integer pageItemNumber = 7;

    /* 更改订单状态 */
    public Integer changeOrderStatus(Integer orderId, Integer status) {
        Order order = orderDao.findById(orderId);
        if (order == null) return OrderConstant.ORDER_NOT_FOUND;
        else {
            order.setStatus(status);
            orderDao.saveAndFlush(order);
            return OrderConstant.SUCCESS;
        }
    };

    /* 下订单 */
    public Integer makeOrder(User user, Goods goods, Integer goodsNum)
    {
        /* 验证商品状态 */
        if (goods.getState() == GoodsConstant.ON_SALE) {
            /* 验证商品买家和卖家是否同一人 */
            if (goods.getUser() == user) return OrderConstant.BUYER_SELLER_SAME;
            /* 验证商品库存是否足够 */
            if (goods.getInventory() < goodsNum) return OrderConstant.GOODS_INVENTORY_NOT_ENOUGH;

            Order order = new Order();
            order.setGoods(goods);
            order.setBuyer(user);
            order.setNum(goodsNum);
            order.setSaleroom(goods.getPrice().multiply(BigDecimal.valueOf(goodsNum)));
            order.setSeller(goods.getUser());
            orderDao.saveAndFlush(order);

            /* 减少商品库存 */
            goods.setInventory(goods.getInventory() - goodsNum);
            if(goods.getInventory() == 0){
                goods.setState(0);
            }
            goodsDao.saveAndFlush(goods);
            return OrderConstant.SUCCESS;
        }
        return GoodsConstant.SOLD_OUT;
    };

    public Integer affirmWants(Integer wantsId)
    {
        Wants wants = wantsDao.findById(wantsId);
        if (wants == null) return OrderConstant.WANTS_NOT_FOUNT;
        else {
            Goods goods = wants.getGoods();
            Integer returnVal = makeOrder(wants.getBuyer(), wants.getGoods(), wants.getNum());
            if (returnVal == OrderConstant.SUCCESS) wantsDao.deleteAllWantsByGoods(goods);
            return returnVal;
        }
    };

    /* 根据用户名获取订单信息 */
    public List<OrderDto> getOrdersByUser(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage) {
        List<OrderDto> buyerOrderList = getOrdersByBuyer(user, startTime, endTime, fetchPage);
        List<OrderDto> sellerOrderList = getOrdersBySeller(user, startTime, endTime, fetchPage);
        buyerOrderList.addAll(sellerOrderList);
        return buyerOrderList;
    };

    public List<OrderDto> getOrdersBySeller(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage) {
        Pageable pageable = PageRequest.of(fetchPage, pageItemNumber, Sort.Direction.DESC, "timestamp");
        List<Order> orderList = new ArrayList<>();
        if (startTime == null && endTime == null) orderList = orderDao.findAllBySeller(user, pageable).getContent();
        else if (startTime != null && endTime != null) orderList = orderDao.findAllBySellerAndTimestampBetween(user, startTime, endTime, pageable).getContent();
        else if (startTime != null) orderList = orderDao.findAllBySellerAndTimestampAfter(user, startTime, pageable).getContent();
        else orderList = orderDao.findAllBySellerAndTimestampBefore(user, endTime, pageable).getContent();
        List<OrderDto> result = OrderDto.convert(orderList);

        return result;
    };

    public List<OrderDto> getOrdersByBuyer(User user, Timestamp startTime, Timestamp endTime, Integer fetchPage) {
        Pageable pageable = PageRequest.of(fetchPage, pageItemNumber, Sort.Direction.DESC, "timestamp");
        List<Order> orderList = new ArrayList<>();
        if (startTime == null && endTime == null) orderList = orderDao.findAllByBuyer(user, pageable).getContent();
        else if (startTime != null && endTime != null) orderList = orderDao.findAllByBuyerAndTimestampBetween(user, startTime, endTime, pageable).getContent();
        else if (startTime != null) orderList = orderDao.findAllByBuyerAndTimestampAfter(user, startTime, pageable).getContent();
        else orderList = orderDao.findAllByBuyerAndTimestampBefore(user, endTime, pageable).getContent();
        List<OrderDto> result = OrderDto.convert(orderList);

        return result;
    };

    public Integer commentAndRateOnOrder(Integer orderId, String comment, Integer rating,Integer userId) {
        Order order = orderDao.findById(orderId);
        if (order == null) return OrderConstant.ORDER_NOT_FOUND;
        if(order.getBuyer().getId().equals(userId)){
            if (!comment.equals("")) order.setComment(comment);
            order.setRating(rating);
            orderDao.saveAndFlush(order);
            //更新卖家信誉分
            User user=order.getSeller();
            List<Order> orders=user.getSellerList();
            Double totleCredit=0.0;
            Integer count=0;
            for(Order it :orders){
               count++;
               totleCredit+=it.getRating();
            }
            Double credit=totleCredit/count;
            user.setCredit(credit);
            userDao.saveAndFlush(user);
            return OrderConstant.SUCCESS;}
        else {
            return -1;
        }

    }

    @Override
    public Integer countOrdersByBuyer(User user) {
        return orderDao.countOrdersByBuyer(user);
    }

    @Override
    public Integer countOrdersBySeller(User user) {
        return orderDao.countOrdersBySeller(user);
    }

}
