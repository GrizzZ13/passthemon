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
@Table(name="history", indexes = {@Index(name="history_userIndex", columnList = "user"),
        @Index(name="history_goodsIndex", columnList = "goods"),
        @Index(name="history_timeIndex", columnList = "time")})
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class History {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;

    @Column(name = "time")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "goods", referencedColumnName = "goods_id")
    private Goods goods;

    public History(){}
}
