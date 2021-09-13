package com.backend.passthemon.service;

import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;

import java.util.List;

public interface ChannelService {
    Channel save(Channel channel);
    Channel getChannelByAuthorAndRecipient(User author, User recipient);
    Integer getChannelIdByAuthorAndRecipient(Integer author, Integer recipient);
    List<Channel> getChannelsByAuthor(User user);
    Integer newChannel(Integer userid_1, Integer userid_2);
}
