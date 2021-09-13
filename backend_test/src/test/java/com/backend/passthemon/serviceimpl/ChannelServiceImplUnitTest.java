package com.backend.passthemon.serviceimpl;


import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import com.backend.passthemon.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ChannelServiceImplUnitTest {
    @Mock
    ChannelRepository channelRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private ChannelServiceImpl channelService;

    private Integer authorId = 1, recipientId = 1, channelId = 1;
    private Channel channel = new Channel(channelId);
    private User author = new User(authorId);
    private User recipient = new User(recipientId);
    private List<Channel> channelList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(channelRepository.save(channel)).thenReturn(channel);
        Mockito.when(channelRepository.getChannelByAuthorAndRecipient(author, recipient)).thenReturn(channel);
    }

    @Test
    public void save() {
        Assert.assertEquals(channelService.save(channel), channel);
        Mockito.verify(channelRepository).save(channel);
    }
    @Test
    public void getChannelByAuthorAndRecipient_User() {
        Assert.assertEquals(channelService.getChannelByAuthorAndRecipient(author, recipient), channel);
        Mockito.verify(channelRepository).getChannelByAuthorAndRecipient(author, recipient);
    }
    @Test
    public void getChannelIdByAuthorAndRecipient_Integer() {
        Mockito.when(channelRepository.getChannelByAuthorAndRecipient(author, recipient)).thenReturn(channel);
        Assert.assertEquals(channelService.getChannelIdByAuthorAndRecipient(authorId, recipientId), channelId);
        Mockito.verify(channelRepository).getChannelByAuthorAndRecipient(author, recipient);
    }
    @Test
    public void getChannelIdByAuthorAndRecipient_Integer_NULL() {
        Mockito.when(channelRepository.getChannelByAuthorAndRecipient(author, recipient)).thenReturn(null);
        Assert.assertNull(channelService.getChannelIdByAuthorAndRecipient(authorId, recipientId));
        Mockito.verify(channelRepository).getChannelByAuthorAndRecipient(author, recipient);
    }
    @Test
    public void getChannelsByAuthor() {
        Mockito.when(channelRepository.getChannelsByAuthor(author)).thenReturn(channelList);
        Assert.assertEquals(channelService.getChannelsByAuthor(author), channelList);
        Mockito.verify(channelRepository).getChannelsByAuthor(author);
    }
    @Test
    public void newChannel() {
        channel.setAuthor(author); channel.setRecipient(recipient); channel.setId(null);
        Channel channel1 = new Channel(author, recipient);
        Mockito.when(channelRepository.getChannelByAuthorAndRecipient(author, recipient)).thenReturn(null);
        Mockito.when(channelRepository.save(channel1)).thenReturn(channel);
        Assert.assertNull(channelService.newChannel(authorId, recipientId));
        Mockito.verify(channelRepository).getChannelByAuthorAndRecipient(author, recipient);
        Mockito.verify(channelRepository).save(channel1);
    }
}
