package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Channel getChannelByAuthorAndRecipient(User author, User recipient) {
        return channelRepository.getChannelByAuthorAndRecipient(author, recipient);
    }

    @Override
    @Cacheable(value = "channel", key = "#author + '-' + #recipient", unless = "#result==null")
    public Integer getChannelIdByAuthorAndRecipient(Integer author, Integer recipient) {
        log.info("[Channel Service] : get channel id by two user id, database operation");
        Channel channel = channelRepository.getChannelByAuthorAndRecipient(new User(author), new User(recipient));
        if (channel==null)
            return null;
        else
            return channel.getId();
    }

    @Override
    public List<Channel> getChannelsByAuthor(User user) {
        return channelRepository.getChannelsByAuthor(user);
    }

    @Override
    @Cacheable(value = "channel", key = "#userid_1 + '-' + #userid_2")
    public Integer newChannel(Integer userid_1, Integer userid_2) {
        User user1 = new User(userid_1);
        User user2 = new User(userid_2);
        Channel channel = channelRepository.getChannelByAuthorAndRecipient(user1, user2);
        if(channel==null) {
            channel = new Channel(userid_1, userid_2);
            channel = channelRepository.save(channel);
        }
        log.info("[Channel Service] : inner newChannel method invoked");
        return channel.getId();
    }
}
