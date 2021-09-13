package com.backend.passthemon.dto;

import com.backend.passthemon.entity.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class GoodsDto {
    private Integer id;
    private BigDecimal price;
    private String name;
    private Integer userId;
    private String image;
    private Integer state;
    private Integer wanterNum;

    public static GoodsDto convert(Goods goods){
        return GoodsDto.builder()
                .id(goods.getId())
                .price(goods.getPrice())
                .name(goods.getName())
                .userId(goods.getUser().getId())
                .state(goods.getState())
                //.image(goods.getImageList().get(0).getImg())
                .wanterNum(goods.getWantsList().size())
                .build();
    }
    public static List<GoodsDto> convert(List<Goods> goodsList){
        List<GoodsDto> result = new LinkedList<>();
        for (Goods goods : goodsList){
            result.add(GoodsDto.convert(goods));
        }
        return result;
    }
}
