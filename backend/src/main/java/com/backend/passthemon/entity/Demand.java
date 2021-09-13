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
@Table(name="demands", indexes = {@Index(name="goods_userAttrition", columnList = "attrition"),
        @Index(name="goods_userCategory", columnList = "category")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demands_id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="num")
    private Integer num;

    @Column(name = "ideal_price", columnDefinition = "decimal(10,2)")
    @Digits(integer = 8, fraction = 2) // 整数最多8位, 小数最多2位
    private BigDecimal idealPrice; /* 需要进行序列化和反序列化定义 */

    @Column(name="description",columnDefinition = "varchar(512)")
    private String description;

    @Column(name="upload_time")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp upLoadTime;

    @Column(name="state")
    private Integer state;

    @Column(name = "category")
    private Integer category;

    @Column(name="attrition")
    private Integer attrition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private User user;

    public Demand(String name,Integer num,BigDecimal idealPrice,String description,Timestamp upLoadTime,User user,Integer state,Integer category,Integer attrition){
        this.name=name;
        this.num=num;
        this.idealPrice=idealPrice;
        this.description=description;
        this.upLoadTime=upLoadTime;
        this.state=state;
        this.user=user;
        this.category=category;
        this.attrition=attrition;
    }

    public Demand(){};
}




