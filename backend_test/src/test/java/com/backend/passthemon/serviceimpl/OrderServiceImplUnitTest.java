package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.constant.OrderConstant;
import com.backend.passthemon.dto.OrderDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;

class OrderServiceImplUnitTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    GoodsRepository goodsRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    private final User user = new User();
    private final Goods goods = new Goods();
    private final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private List<OrderDto> orderDtoList;
    private final Integer goodsNum = 1;
    private final Integer fetchPage = 0;
    private final Integer goodsId = 1;
    private final Integer orderId = 1;
    private final Integer status = 1;
    private final Integer wantsId = 1;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(orderRepository.affirmWants(wantsId)).thenReturn(OrderConstant.SUCCESS);
        Mockito.when(orderRepository.changeOrderStatus(orderId, status)).thenReturn(OrderConstant.SUCCESS);
        Mockito.when(goodsRepository.findGoodsById(goodsId)).thenReturn(goods);
        Mockito.when(goodsRepository.findGoodsById(~goodsId)).thenReturn(null);
        Mockito.when(orderRepository.makeOrder(user, goods, goodsNum)).thenReturn(OrderConstant.SUCCESS);
        Mockito.when(orderRepository.makeOrder(null, goods, goodsNum)).thenReturn(OrderConstant.GOODS_NOT_FOUNT);
        Mockito.when(orderRepository.getOrdersByUser(user, timestamp, timestamp, fetchPage)).thenReturn(orderDtoList);
        Mockito.when(orderRepository.getOrdersBySeller(user, timestamp, timestamp, fetchPage)).thenReturn(orderDtoList);
        Mockito.when(orderRepository.getOrdersByBuyer(user, timestamp, timestamp, fetchPage)).thenReturn(orderDtoList);
    };

    @Test
    public void changeOrderStatus() {
        Integer returnVal = orderService.changeOrderStatus(orderId, status);
        Assert.assertEquals(returnVal.toString(), String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(orderRepository).changeOrderStatus(orderId, status);
    }
    @Test
    public void makeOrder() {
        Integer returnVal = orderService.makeOrder(user, goodsId, goodsNum);
        Assert.assertEquals(returnVal.toString(), String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(goodsRepository).findGoodsById(goodsId);
        Mockito.verify(orderRepository).makeOrder(user, goods, goodsNum);

        returnVal = orderService.makeOrder(user, ~goodsId, goodsNum);
        Assert.assertEquals(returnVal.toString(), String.valueOf(OrderConstant.GOODS_NOT_FOUNT));
        Mockito.verify(goodsRepository).findGoodsById(~goodsId);

    }
    @Test
    public void affirmWants() {
        Integer returnVal = orderService.affirmWants(wantsId);
        Assert.assertEquals(returnVal.toString(), String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(orderRepository).affirmWants(wantsId);
    }
    @Test
    public void getOrdersByUser() {
        Assert.assertEquals(orderService.getOrdersByUser(user, timestamp, timestamp, fetchPage), orderDtoList);
        Mockito.verify(orderRepository).getOrdersByUser(user, timestamp, timestamp, fetchPage);
    }

    @Test
    public void getOrdersAsBuyerByUser() {
        Assert.assertEquals(orderService.getOrdersAsBuyerByUser(user, timestamp, timestamp, fetchPage), orderDtoList);
        Mockito.verify(orderRepository).getOrdersByBuyer(user, timestamp, timestamp, fetchPage);
    }

    @Test
    public void getOrdersAsSellerBuUser() {
        Assert.assertEquals(orderService.getOrdersAsSellerByUser(user, timestamp, timestamp, fetchPage), orderDtoList);
        Mockito.verify(orderRepository).getOrdersBySeller(user, timestamp, timestamp, fetchPage);
    }

    @Test
    public void commentAndRateOnOrder() {
        String comment = "东东好帅";
        Integer orderId = 1, rating = 5;
        Assert.assertEquals(orderService.commentAndRateOnOrder(orderId, comment, rating).toString(), String.valueOf(OrderConstant.SUCCESS));
        Mockito.verify(orderRepository).commentAndRateOnOrder(orderId, comment, rating);
    }
}
