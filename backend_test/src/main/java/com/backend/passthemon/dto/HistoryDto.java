package com.backend.passthemon.dto;

import com.backend.passthemon.entity.History;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class HistoryDto {
    private Integer id;         //是goodId
    private String name;
    private String image;
    private Timestamp time;
    private BigDecimal price;
    private Integer userId;

    public static HistoryDto convert(History history){
        return HistoryDto.builder()
                .id(history.getGoods().getId())
                .name(history.getGoods().getName())
                .time(history.getTime())
                .price(history.getGoods().getPrice())
                .userId(history.getGoods().getUser().getId())
                .build();
    }
    public static List<HistoryDto> convert(List<History> historyList){
        List<HistoryDto> result = new LinkedList<>();
        for (History history : historyList){
            result.add(HistoryDto.convert(history));
        }
        return result;
    }

}
