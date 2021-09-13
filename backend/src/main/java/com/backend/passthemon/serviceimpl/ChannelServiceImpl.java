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
    public Integer getChannelIdByAuthorAndRecipient(Integer author, Integer recipient) {
        return channelRepository.getChannelIdByAuthorIdAndRecipientId(author, recipient);
    }

    @Override
    public List<Channel> getChannelsByAuthor(User user) {
        return channelRepository.getChannelsByAuthor(user);
    }

    @Override
    public Integer newChannel(Integer userid_1, Integer userid_2) {
        log.info("[Channel Service] : inner newChannel method invoked");
        Integer channelId = channelRepository.getChannelIdByAuthorIdAndRecipientId(userid_1, userid_2);
        if(channelId != null) {
            return channelId;
        }
        else{
            Channel channel = new Channel(userid_1, userid_2);
            channel = channelRepository.save(channel);
            return channel.getId();
        }
    }
}
