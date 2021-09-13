package com.backend.passthemon.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="user", indexes = {@Index(name="user_idIndex", columnList = "user_id"),
    @Index(name="user_emailIndex", columnList = "email")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class User {

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "buyer")
    private List<Order> buyerList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Order> sellerList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Follow> followList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<History> historyList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Favorite> favoriteList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Goods> goodsList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Demand> demandList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "buyer")
    private List<Wants> wantsBuyerList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller")
    private List<Wants> wantsSellerList = new ArrayList<>();

    @JsonIgnore
    @JSONField(serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER,orphanRemoval = true, mappedBy = "user")
    private List<Authority> authorityList=new ArrayList<>();

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username="SJTUer";

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Integer state;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "phone", columnDefinition = "varchar(64)")
    private String phone;

    @Column(name = "image", columnDefinition = "longText")
    private String image;

    @Column(name = "refresh_token", columnDefinition = "varchar(1024)")
    private String refreshToken;

    public User(){};

    public User(Integer id){
        this.id = id;
    }
}
