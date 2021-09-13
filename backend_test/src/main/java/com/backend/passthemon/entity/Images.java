package com.backend.passthemon.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
@Document(collection="Images")
public class Images {
    @Id
    @Field("id")
    private Integer id;

    @Field("image")
    private String img;

    @Field("goods_id")
    private Integer goodsId;

    @Field("displayOrder")
    private Integer displayOrder;

    public Images(){};

    public Images(String img, Integer goodsId, Integer displayOrder){
        this.img = img;
        this.goodsId = goodsId;
        this.displayOrder = displayOrder;
    }
}
