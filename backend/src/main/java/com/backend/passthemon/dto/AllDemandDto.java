package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Demand;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class AllDemandDto {
    private Integer id;
    private String name;
    private Integer num;
    private BigDecimal idealPrice;
    private String description;
    private String upLoadTime;
    private Integer state;
    private String uploadUsername;
    private String uploadUserImage;
    private Integer uploadUserId;

    public static AllDemandDto convert(Demand demand){
        return AllDemandDto.builder()
                .id(demand.getId())
                .name(demand.getName())
                .num(demand.getNum())
                .idealPrice(demand.getIdealPrice())
                .description(demand.getDescription())
                .upLoadTime(demand.getUpLoadTime().toString().substring(0, 19))
                .state(demand.getState())
                .uploadUsername(demand.getUser().getUsername())
                .uploadUserImage(demand.getUser().getImage())
                .uploadUserId(demand.getUser().getId())
                .build();
    }
    public static List<AllDemandDto> convert(List<Demand> demandsList){
        List<AllDemandDto> result = new LinkedList<>();
        for (Demand demand : demandsList){
            result.add(AllDemandDto.convert(demand));
        }
        return result;
    }
}
