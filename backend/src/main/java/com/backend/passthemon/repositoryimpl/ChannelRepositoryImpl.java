package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.ChannelDao;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import com.backend.passthemon.service.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class ChannelRepositoryImpl implements ChannelRepository {

    @Autowired
    ChannelDao channelDao;

    @Autowired
    CacheManager cacheManager;

    @Override
    public Channel save(Channel channel) {
        channel = channelDao.saveAndFlush(channel);
        String key = channel.getAuthor().getId().toString() + '-' + channel.getRecipient().getId().toString();
        Objects.requireNonNull(cacheManager.getCache("channel")).put(key, channel.getId());
        return channel;
    }

    @Override
    public Channel getChannelByAuthorAndRecipient(User author, User recipient) {
        return channelDao.getChannelByAuthorAndRecipient(author, recipient);
    }

    @Override
    @Cacheable(value = "channel", key = "#author + '-' + #recipient", unless = "#result==null")
    public Integer getChannelIdByAuthorIdAndRecipientId(Integer author, Integer recipient) {
        log.info("[Channel Service] : get channel id by two user id, database operation");
        Channel channel = channelDao.getChannelByAuthorAndRecipient(new User(author), new User(recipient));
        if (channel==null){
            return null;
        }
        else{
            return channel.getId();
        }
    }

    @Override
    public List<Channel> getChannelsByAuthor(User user) {
        return channelDao.getChannelsByAuthor(user);
    }
}
