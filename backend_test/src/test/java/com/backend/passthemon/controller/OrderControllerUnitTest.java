package com.backend.passthemon.controller;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.constant.OrderConstant;
import com.backend.passthemon.controller.OrderController;
import com.backend.passthemon.dto.OrderDto;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.service.OrderService;
import com.backend.passthemon.service.UserService;
import com.backend.passthemon.utils.msgutils.Msg;
import com.backend.passthemon.utils.msgutils.MsgUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/* 需要将 JwtInterceptor 类中内容注释掉 */
@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest {
    @MockBean
    private OrderService orderService;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    private final User user = new User();
    private List<OrderDto> orderDtoList = new LinkedList<>();

    @Test
    public void testMakeOrder() {
        Integer userId = 1, goodsId = 1, goodsNum = 1;

        /* 给出预测行为 */
        given(this.userService.getUserByUserId(userId)).willReturn(user);
        given(this.orderService.makeOrder(user, goodsId, goodsNum)).willReturn(OrderConstant.SUCCESS);

        try {
            JSONObject jsonObject = new JSONObject(); jsonObject.put("returnVal", OrderConstant.SUCCESS);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.post(
                    "/order/makeOrder?userId=" + userId.toString() + "&goodsId=" + goodsId.toString()
                            + "&goodsNum=" + goodsNum.toString()))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    @Test
    public void testGetOrdersByUserId() {
        Integer userId = 1, fetchPage = 0;

        /* 给出预测行为 */
        given(this.userService.getUserByUserId(userId)).willReturn(user);
        given(this.orderService.getOrdersByUser(user, null, null, fetchPage)).willReturn(orderDtoList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orders", orderDtoList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/order/getOrdersByUserId?userId=" +
                    userId.toString() + "&fetchPage=" + fetchPage.toString()))
                    .andExpect(status().isOk()); // .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }

    @Test
    public void testGetOrdersAsBuyerUserid() {
        Integer userId = 1, fetchPage = 0;

        /* 给出预测行为 */
        given(this.userService.getUserByUserId(userId)).willReturn(user);
        given(this.orderService.getOrdersAsBuyerByUser(user, null, null, fetchPage)).willReturn(orderDtoList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orders", orderDtoList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/order/getOrdersAsBuyerByUserid?userId=" + userId.toString() + "&fetchPage=" + fetchPage.toString()))
                    .andExpect(status().isOk()); // .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
    @Test
    public void testGetOrdersAsSellerUserid() {
        Integer userId = 1, fetchPage = 0;

        /* 给出预测行为 */
        given(this.userService.getUserByUserId(userId)).willReturn(user);
        given(this.orderService.getOrdersAsSellerByUser(user, null, null, fetchPage)).willReturn(orderDtoList);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orders", orderDtoList);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/order/getOrdersAsSellerByUserid?userId=" + userId.toString() + "&fetchPage=" + fetchPage.toString()))
                    .andExpect(status().isOk()); // .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
    @Test
    public void testCommentAndRateOnOrder() {
        Integer orderId = 1, rating = 5;
        String comment = "东东好帅";

        given(this.orderService.commentAndRateOnOrder(orderId, comment, rating)).willReturn(OrderConstant.SUCCESS);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("returnVal", OrderConstant.SUCCESS);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/order/commentAndRateOnOrder?orderId=" + orderId.toString() +
                    "&comment=" + comment + "&rating=" + rating.toString()))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testChangeOrderStatus() {
        Integer orderId = 1, status = 1;

        given(this.orderService.changeOrderStatus(orderId, status)).willReturn(OrderConstant.SUCCESS);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("returnVal", OrderConstant.SUCCESS);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/order/changeOrderStatus?orderId=" + orderId.toString() +
                    "&status=" + status.toString()))
                    .andExpect(status().isOk()).andExpect(jsonPath(".data").value(msg.getData())); // .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAffirmWants() {
        Integer wantsId = 1;

        given(this.orderService.affirmWants(wantsId)).willReturn(OrderConstant.SUCCESS);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("returnVal", OrderConstant.SUCCESS);
            Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonObject);

            mvc.perform(MockMvcRequestBuilders.get("/order/affirmWants?wantsId=" + wantsId.toString()))
                    .andExpect(status().isOk()).andExpect(jsonPath(".data").value(msg.getData())); // .andExpect(jsonPath("$.data").value(msg.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
