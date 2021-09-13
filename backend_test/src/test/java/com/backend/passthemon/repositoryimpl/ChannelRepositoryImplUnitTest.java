package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.ChannelDao;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ChannelRepositoryImplUnitTest {
    @Mock
    ChannelDao channelDao;

    @InjectMocks
    ChannelRepositoryImpl channelRepository;

    private Channel channel;

    private User author, recipient;

    private List<Channel> channelList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(channelDao.saveAndFlush(channel)).thenReturn(channel);
        Mockito.when(channelDao.getChannelByAuthorAndRecipient(author, recipient)).thenReturn(channel);
        Mockito.when(channelDao.getChannelsByAuthor(author)).thenReturn(channelList);
    }

    @Test
    public void save() {
        Assert.assertEquals(channelRepository.save(channel), channel);
        Mockito.verify(channelDao).saveAndFlush(channel);
    }
    @Test
    public void getChannelByAuthorAndRecipient() {
        Assert.assertEquals(channelRepository.getChannelByAuthorAndRecipient(author, recipient), channel);
        Mockito.verify(channelDao).getChannelByAuthorAndRecipient(author, recipient);
    }
    @Test
    public void getChannelsByAuthor() {
        Assert.assertEquals(channelRepository.getChannelsByAuthor(author), channelList);
        Mockito.verify(channelDao).getChannelsByAuthor(author);
    }
}
