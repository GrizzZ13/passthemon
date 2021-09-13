package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.redis.RedisService;
import com.backend.passthemon.repository.ChannelRepository;
import com.backend.passthemon.repository.ChatRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;

public class ChatServiceImplUnitTest {
    @Mock
    ChatRepository chatRepository;
    @Mock
    ChannelRepository channelRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    RedisService redisService;

    @InjectMocks
    private ChatServiceImpl chatService;

    private Integer channelId = 1, authorId = 1, recipientId = 2, counts = 1, pageNumber = 2;

    private User author = new User(authorId), recipient = new User(recipientId);

    private Chat chat = new Chat();

    private ChatDto chatDto = new ChatDto();

    private Channel channel1 = new Channel(), channel2 = new Channel();

    private List<Chat> chatList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Mockito.when(channelRepository.getChannelByAuthorAndRecipient(author, recipient)).thenReturn(channel1);
        Mockito.when(channelRepository.getChannelByAuthorAndRecipient(recipient, author)).thenReturn(channel2);
        Mockito.when(chatRepository.getChatHistoryByChannelsAndOffset(channel1, channel2, counts, pageNumber)).thenReturn(chatList);
    }
    @Test
    public void save() {
        chatService.save(chat);
        Mockito.verify(chatRepository).save(chat);
    }
    @Test
    public void saveAll() {
        Mockito.when(chatRepository.saveAll(null)).thenReturn(null);
        Assert.assertNull(chatService.saveAll(null));
        Mockito.verify(chatRepository).saveAll(null);
    }
    @Test
    public void saveInRedis() {
        chatDto.setChannelId(channelId);
        String key = "chatDto::channelId-" + chatDto.getChannelId();
        chatService.saveInRedis(chatDto);
        Mockito.verify(redisService).rPush(key, chatDto);
    }
    @Test
    public void getChatHistoryByChannelsAndOffset() {
        Assert.assertEquals(chatService.getChatHistoryByChannelsAndOffset(authorId, recipientId, counts, pageNumber), chatList);
        Mockito.verify(channelRepository).getChannelByAuthorAndRecipient(author, recipient);
        Mockito.verify(channelRepository).getChannelByAuthorAndRecipient(recipient, author);
        Mockito.verify(chatRepository).getChatHistoryByChannelsAndOffset(channel1, channel2, counts, pageNumber);
    }
    @Test
    public void redis2Mysql() {
        String prefix = KeyUtil.ChatKeyPrefix();
        Set<String> chatKeySet = new HashSet<>();;
        String chatKey = "chatKey";
        chatKeySet.add(chatKey);
        List<Object> objectList = new ArrayList<>();
        objectList.add(chatDto);
        List<Chat> chatList = new LinkedList<>();
        chatList.add(chatDto.newChat());

        Mockito.when(redisService.keys(prefix)).thenReturn(chatKeySet);
        Mockito.when(redisService.range(chatKey, 0, -1)).thenReturn(objectList);

        chatService.redis2Mysql();
        Mockito.verify(chatRepository).saveAll(chatList);
        Mockito.verify(redisService).remove(chatKey);
    }
    @Test
    public void redis2Mysql_Exception() {
        String prefix = KeyUtil.ChatKeyPrefix();
        Set<String> chatKeySet = new HashSet<>();;
        String chatKey = "chatKey";
        chatKeySet.add(chatKey);
        List<Object> objectList = new ArrayList<>();
        objectList.add(chatDto);
        List<Chat> chatList = new LinkedList<>();
        chatList.add(chatDto.newChat());

        Mockito.when(redisService.keys(prefix)).thenReturn(chatKeySet);
        Mockito.when(redisService.range(chatKey, 0, -1)).thenReturn(objectList);

        Exception exception = new RuntimeException();
        Mockito.when(chatRepository.saveAll(chatList)).thenThrow(exception);
        chatService.redis2Mysql();
        Mockito.verify(chatRepository).saveAll(chatList);
        Mockito.verify(redisService).remove(chatKey);
    }
}
