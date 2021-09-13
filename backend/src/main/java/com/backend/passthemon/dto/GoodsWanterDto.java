package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Wants;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class GoodsWanterDto {
    private Integer id;
    private Integer wanterId;
    private String wanterImage;
    private String wanterName;
    private Integer num;
    private String time;

    public static GoodsWanterDto convert(Wants wants){
        return GoodsWanterDto.builder()
                .id(wants.getId())
                .wanterId(wants.getBuyer().getId())
                .wanterImage(wants.getBuyer().getImage())
                .wanterName(wants.getBuyer().getUsername())
                .num(wants.getNum())
                .time(wants.getTimestamp().toString().substring(0, 19))
                .build();
    }

    public static List<GoodsWanterDto> convert(List<Wants> wantsList){
        List<GoodsWanterDto> result = new LinkedList<>();
        for (Wants wants : wantsList){
            result.add(GoodsWanterDto.convert(wants));
        }
        return result;
    }
}
