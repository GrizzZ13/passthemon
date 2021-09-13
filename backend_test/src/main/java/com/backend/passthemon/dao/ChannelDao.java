package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelDao extends JpaRepository<Channel, Integer> {
    Channel getChannelByAuthorAndRecipient(User author, User recipient);
    List<Channel> getChannelsByAuthor(User user);

}

