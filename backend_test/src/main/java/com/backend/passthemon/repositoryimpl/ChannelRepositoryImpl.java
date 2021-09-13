package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.ChannelDao;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository {

    @Autowired
    ChannelDao channelDao;

    @Override
    public Channel save(Channel channel) {
        return channelDao.saveAndFlush(channel);
    }

    @Override
    public Channel getChannelByAuthorAndRecipient(User author, User recipient) {
        return channelDao.getChannelByAuthorAndRecipient(author, recipient);
    }

    @Override
    public List<Channel> getChannelsByAuthor(User user) {
        return channelDao.getChannelsByAuthor(user);
    }
}
