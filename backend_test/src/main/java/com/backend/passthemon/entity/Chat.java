package com.backend.passthemon.entity;

import com.backend.passthemon.constant.ChatConstant;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicUpdate /* 使用数据库自增主键 */
@DynamicInsert
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "channel", referencedColumnName = "channel_id")
    private Channel channel;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    @Column(name = "accepted")
    private boolean accepted = ChatConstant.REJECTED;

    public Chat(Channel channel, String content, Date date, boolean accepted) {
        this.channel = channel;
        this.content = content;
        this.date = date;
        this.accepted = accepted;
    }

    public Chat(Channel channel, String content, Date date) {
        this.channel = channel;
        this.content = content;
        this.date = date;
    }

    public Chat() {
    }
}
