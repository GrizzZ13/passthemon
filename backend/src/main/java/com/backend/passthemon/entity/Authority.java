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
@Table(name="authority")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"}) /* 防止循环引用 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="authority_name")
    private String authorityName;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    private User user;

    public Authority(String authorityName,User user){
        this.authorityName=authorityName;
        this.user=user;
    }
    private Authority(){}
}
