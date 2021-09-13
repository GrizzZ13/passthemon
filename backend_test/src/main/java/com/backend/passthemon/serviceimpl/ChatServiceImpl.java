package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import com.backend.passthemon.repository.ChatRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.ChatService;
import com.backend.passthemon.redis.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    @Override
    public void save(Chat chat) {
        /*
        previous version : directly save chat history in mysql
        chatRepository.save(chat);
        current  version : save them in redis
         */
        chatRepository.save(chat);
    }

    @Override
    public <S extends Chat> List<S> saveAll(Iterable<S> entities) {
        return chatRepository.saveAll(entities);
    }

    @Override
    public void saveInRedis(ChatDto chatDto) {
        String key = "chatDto::channelId-" + chatDto.getChannelId();
        redisService.rPush(key, chatDto);
    }

    @Override
    public List<Chat> getChatHistoryByChannelsAndOffset(Integer author, Integer recipient, Integer counts, Integer pageNumber) {
        User authorUser = new User(author);
        User recipientUser = new User(recipient);
        Channel channel1 = channelRepository.getChannelByAuthorAndRecipient(authorUser, recipientUser);
        Channel channel2 = channelRepository.getChannelByAuthorAndRecipient(recipientUser, authorUser);
        return chatRepository.getChatHistoryByChannelsAndOffset(channel1, channel2, counts, pageNumber);
    }

    @Scheduled(cron = "0 0 0/3 * * *")
    public void redis2Mysql() {
        log.info("[Chat Service] : scheduled task");
        String prefix = KeyUtil.ChatKeyPrefix();
        Set<String> chatKeySet =  redisService.keys(prefix);
        for (String chatKey : chatKeySet){
            try{
                List<Object> objectList = redisService.range(chatKey, 0, -1);
                List<Chat> chatList = new LinkedList<>();
                for (Object object : objectList){
                    ChatDto chatDto = (ChatDto) object;
                    Chat chat = chatDto.newChat();
                    chatList.add(chat);
                }
                chatRepository.saveAll(chatList);
                redisService.remove(chatKey);
            }
            catch (Exception e){
                redisService.remove(chatKey);
            }
        }
    }
}
