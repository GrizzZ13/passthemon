package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.History;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class FavoriteDto {
    private Integer id;    //goodsId
    private String name;
    private String image;
    private BigDecimal price;
    private Integer userId;
    private String username;
    private String userImage;

    public static FavoriteDto convert(Favorite favorite){
        return FavoriteDto.builder()
                .id(favorite.getGoods().getId())
                .name(favorite.getGoods().getName())
                .price(favorite.getGoods().getPrice())
                .userId(favorite.getGoods().getUser().getId())
                .username(favorite.getGoods().getUser().getUsername())
                .userImage(favorite.getGoods().getUser().getImage())
                .build();
    }

    public static List<FavoriteDto> convert(List<Favorite> favoriteList){
        List<FavoriteDto> result = new LinkedList<>();
        for (Favorite favorite : favoriteList){
            result.add(FavoriteDto.convert(favorite));
        }
        return result;
    }
}
