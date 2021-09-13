package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;

import java.util.List;

public interface ChannelRepository {
    Channel save(Channel channel);
    Channel getChannelByAuthorAndRecipient(User author, User recipient);
    Integer getChannelIdByAuthorIdAndRecipientId(Integer author, Integer recipient);
    List<Channel> getChannelsByAuthor(User user);
}
