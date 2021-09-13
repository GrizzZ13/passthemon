package com.backend.passthemon.dto;

import com.backend.passthemon.entity.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Integer id;
    private Integer num;
    private BigDecimal saleroom; // 销售额
    private String timestamp;
    private String goodsName;
    private Integer buyer;
    private Integer seller;
    private Integer goods;
    private String image;
    private Integer status;
    private Integer rating;
    private String comment;


    public static OrderDto convert(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .num(order.getNum())
                .saleroom(order.getSaleroom())
                .buyer(order.getBuyer().getId())
                .seller(order.getSeller().getId())
                .goods(order.getGoods().getId())
                .goodsName(order.getGoods().getName())
                .timestamp(order.getTimestamp().toString().substring(0, 19))
                .status(order.getStatus())
                .rating(order.getRating())
                .comment(order.getComment())
                .build();
    }
    public static List<OrderDto> convert(List<Order> orderList){
        List<OrderDto> result = new LinkedList<>();
        for (Order order : orderList){
            result.add(OrderDto.convert(order));
        }
        return result;
    }
}
