package com.backend.passthemon.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="verification")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Verification {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "code")
    private String code;
    @Column(name = "time")
    private Long time;
}
