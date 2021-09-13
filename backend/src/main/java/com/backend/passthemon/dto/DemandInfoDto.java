package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Demand;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DemandInfoDto {
    private Integer demandId;
    private String name;
    private Integer num;
    private BigDecimal idealPrice;
    private String description;
    private Integer state;
    private Integer category;
    private Integer attrition;

    public DemandInfoDto(Demand demand){
        this.demandId=demand.getId();
        this.name=demand.getName();
        this.num=demand.getNum();
        this.idealPrice=demand.getIdealPrice();
        this.description=demand.getDescription();
        this.state=demand.getState();
        this.category=demand.getCategory();
        this.attrition=demand.getAttrition();
    }
    public DemandInfoDto(Integer demandId,String name,Integer num,BigDecimal idealPrice,String description,Integer category,Integer attrition){
        this.demandId=demandId;
        this.name=name;
        this.num=num;
        this.idealPrice=idealPrice;
        this.description=description;
        this.category=category;
        this.attrition=attrition;
    }
    public DemandInfoDto(){}
}
