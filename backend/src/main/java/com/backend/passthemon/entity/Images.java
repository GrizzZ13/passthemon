package com.backend.passthemon.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Data
@Document(collection="Images")
public class Images {
    @Id
    @Field("id")
    private Integer id;

    @Field("goods_id")
    @HashIndexed
    private Integer goodsId;

    @Field("images_list")
    private List<String> imageList;

    public Images(){};

    public Images(Integer goodsId, List<String> imageList){
        this.goodsId = goodsId;
        this.imageList = imageList;
    }
}
