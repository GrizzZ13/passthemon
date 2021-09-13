package com.backend.passthemon.dto;

import com.backend.passthemon.constant.ChatConstant;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class ChatDto implements Comparable<ChatDto> {
    private Integer channelId;
    private Integer author;
    private Integer recipient;
    private String content;
    private String date;
    private Integer type;

    public static ChatDto convert(Chat chat){
        return ChatDto.builder()
                .channelId(chat.getChannel().getId())
                .author(chat.getChannel().getAuthor().getId())
                .recipient(chat.getChannel().getRecipient().getId())
                .content(chat.getContent())
                .date(chat.getDate().toString())
                .type(ChatConstant.USER)
                .build();
    }

    public static List<ChatDto> convert(List<Chat> chatList){
        List<ChatDto> chatDtoList = new LinkedList<>();
        for(Chat chat : chatList){
            chatDtoList.add(ChatDto.convert(chat));
        }
        return chatDtoList;
    }

    public ChatDto(Integer author, Integer recipient, String content, String date) {
        this.author = author;
        this.recipient = recipient;
        this.content = content;
        this.date = date;
        this.type = ChatConstant.USER;
    }

    public ChatDto(Integer author, Integer recipient, String content, String date, Integer type) {
        this.author = author;
        this.recipient = recipient;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public ChatDto(Integer channelId, Integer author, Integer recipient, String content, String date) {
        this.channelId = channelId;
        this.author = author;
        this.recipient = recipient;
        this.content = content;
        this.date = date;
        this.type = ChatConstant.USER;
    }

    public ChatDto(Integer channelId, Integer author, Integer recipient, String content, String date, Integer type) {
        this.channelId = channelId;
        this.author = author;
        this.recipient = recipient;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public ChatDto(){};

    public Chat newChat(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date;
        try{
            date = format.parse(this.date);
        }
        catch (Exception e) {
            date = null;
        }
        return new Chat(new Channel(this.channelId), this.content, date, ChatConstant.ACCEPTED);
    }

    @Override
    public int compareTo(ChatDto o) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       try{
           Date date1 = format.parse(this.date);
           Date date2 = format.parse(o.date);
           return date2.compareTo(date1);
       }
       catch (Exception e) {
           return 0;
       }
    }
}
