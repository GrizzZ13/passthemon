package com.backend.passthemon.entity;

import com.backend.passthemon.constant.OrderConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="orders", indexes = {@Index(name="order_sellerIndex", columnList = "seller"),
    @Index(name="order_buyerIndex", columnList = "buyer"),
    @Index(name = "order_timestampIndex", columnList = "timestamp")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "num")
    private Integer num;

    @Column(name = "status")
    @ColumnDefault("0")
    private Integer status;

    @Column(name = "rating", insertable = false, columnDefinition = "int default '0'")
    private Integer rating;

    @Column(name = "comment", insertable = false, columnDefinition = "varchar(255) default 'NoComment'")
    private String comment;

    @Column(name = "saleroom", columnDefinition = "decimal(10,2)")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal saleroom; // 销售额

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

    public Order() {};
    /* 测试专用*/
    public Order(User seller, User buyer, Timestamp timestamp) {
        this.seller = seller;
        this.buyer = buyer;
        this.timestamp = timestamp;
    }
    public Order(Integer num, Integer rating, String comment, @Digits(integer = 8, fraction = 2) BigDecimal saleroom, Timestamp timestamp, User buyer, User seller, Goods goods) {
        this.num = num;
        this.rating = rating;
        this.comment = comment;
        this.saleroom = saleroom;
        this.timestamp = timestamp;
        this.buyer = buyer;
        this.seller = seller;
        this.goods = goods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getSaleroom() {
        return saleroom;
    }

    public void setSaleroom(BigDecimal saleroom) {
        this.saleroom = saleroom;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
