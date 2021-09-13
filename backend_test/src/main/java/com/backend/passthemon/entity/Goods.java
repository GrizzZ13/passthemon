package com.backend.passthemon.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="goods", indexes = {@Index(name="goods_idIndex", columnList = "goods_id"),
        @Index(name="goods_stateIndex", columnList = "state"),
        @Index(name="goods_userIndex", columnList = "user"),
        @Index(name="goods_userAttrition", columnList = "attrition"),
        @Index(name="goods_userCategory", columnList = "category")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Goods {
    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "goods")
    private List<Order> goodsList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "goods")
    private List<History> historyList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "goods")
    private List<Favorite> favoriteList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "goods")
    private List<Wants> wantsList = new ArrayList<>();

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "goods_id")
    private Integer id;

    @Column(name = "price", columnDefinition = "decimal(10,2)")
    @Digits(integer = 8, fraction = 2) // 整数最多8位, 小数最多2位
    private BigDecimal price; /* 需要进行序列化和反序列化定义 */

    @Column(name = "name")
    private String name;

    @Column(name = "inventory")
    private Integer inventory;

    @Column(name = "description", columnDefinition = "varchar(512)")
    private String description;

    @Column(name = "upload_time")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp uploadTime;

    @Column(name = "state")
    private Integer state;

    @Column(name = "category")
    private Integer category;

    @Column(name="attrition")
    private Integer attrition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private User user;

    @Transient
    private List<Images> imagesList = new ArrayList<>();

    public Goods(BigDecimal price,String name,Integer inventory,String description,Timestamp uploadTime,User user,Integer state,Integer category,Integer attrition)
    {
       this.price=price;
       this.name=name;
       this.inventory=inventory;
       this.description=description;
       this.uploadTime=uploadTime;
       this.user=user;
       this.state=state;
       this.category=category;
       this.attrition=attrition;
    }
    public Goods(){}

    public Goods(Integer goodsId){
        this.id = goodsId;
    }

    public Goods(Integer goodsId, List<Images> imagesList){
        this.id = goodsId;
        this.imagesList = imagesList;
    }
}
