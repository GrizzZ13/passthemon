package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Channel;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ChannelDto {
    private Integer channelId;
    private Integer authorId;
    private Integer recipientId;
    private String avatar;
    private String authorUsername;
    private String recipientUsername;

    public static ChannelDto convert(Channel channel){
        return ChannelDto.builder()
                .channelId(channel.getId())
                .authorId(channel.getAuthor().getId())
                .recipientId(channel.getRecipient().getId())
                .avatar(channel.getRecipient().getImage())
                .authorUsername(channel.getAuthor().getUsername())
                .recipientUsername(channel.getRecipient().getUsername())
                .build();
    }

    public static List<ChannelDto> convert(List<Channel> channels){
        List<ChannelDto> channelDtoList = new ArrayList<>();
        for (Channel channel : channels){
            channelDtoList.add(ChannelDto.convert(channel));
        }
        return channelDtoList;
    }

}
