package com.backend.passthemon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "wants", indexes={@Index(name="wants_buyerIndex", columnList = "buyer"),
        @Index(name = "wants_goodsIndex", columnList = "goods"),
        @Index(name = "wants_sellerIndex", columnList = "seller")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Wants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreationTimestamp
    @Column(name = "timestamp")
    @JsonFormat(pattern = "%Y-%m-%d %H:%i:%S", timezone = "GMT+8")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "buyer", referencedColumnName = "user_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller", referencedColumnName = "user_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "goods", referencedColumnName = "goods_id")
    private Goods goods;

    @Column(name = "num")
    private Integer num;
}
