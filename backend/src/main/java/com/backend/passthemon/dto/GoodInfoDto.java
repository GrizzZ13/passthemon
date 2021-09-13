package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Images;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class GoodInfoDto {
    private Integer goodId;
    private String description;
    private Integer inventory;
    private String name;
    private BigDecimal price;
    private Integer category;
    private Integer attrition;
    private List<String> images=new ArrayList<>();
    public GoodInfoDto(Goods goods){
        this.goodId=goods.getId();
        this.description=goods.getDescription();
        this.inventory=goods.getInventory();
        this.price=goods.getPrice();
        this.name=goods.getName();
        List<String> img=goods.getImagesList();
        for(int i=0;i<img.size();i++){
            images.add(img.get(i));
        }
        this.category=goods.getCategory();
        this.attrition=goods.getAttrition();
    }
    public GoodInfoDto(Integer goodId,String description,Integer inventory,String name, BigDecimal price,List<String> images,Integer category,Integer attrition){
        this.goodId=goodId;
        this.description=description;
        this.inventory=inventory;
        this.name=name;
        this.price=price;
        this.images=images;
        this.category=category;
        this.attrition=attrition;
    }
    public GoodInfoDto(){}
}
