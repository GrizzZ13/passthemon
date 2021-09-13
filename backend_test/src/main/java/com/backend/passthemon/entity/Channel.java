package com.backend.passthemon.entity;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "channel")
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
public class Channel {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "channel_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    private User author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", referencedColumnName = "user_id")
    private User recipient;

    @Column(name = "visible")
    private boolean visible = true;

    public Channel(){};

    public Channel(Integer id){
        this.id = id;
    }

    public Channel(User author, User recipient){
        this.author = author;
        this.recipient = recipient;
    }

    public Channel(Integer author, Integer recipient){
        this.author = new User(author);
        this.recipient = new User(recipient);
    }

    public Channel(Integer author, Integer recipient, boolean visible){
        this.author = new User(author);
        this.recipient = new User(recipient);
        this.visible = visible;
    }

}
