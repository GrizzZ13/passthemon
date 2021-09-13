package com.backend.passthemon.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name="favorite", indexes = {@Index(name = "favorite_goodsIndex", columnList = "goods"),
    @Index(name="favorite_userIndex", columnList = "user")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Favorite {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "goods", referencedColumnName = "goods_id")
    private Goods goods;
}
