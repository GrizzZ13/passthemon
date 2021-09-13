package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.ChannelRepository;
import com.backend.passthemon.repository.ChatRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.ChatService;
import com.backend.passthemon.service.RedisService;
import com.backend.passthemon.utils.keyutils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Override
    public List<Chat> getChatHistoryByPage(Integer author, Integer recipient, Integer page) {
        Integer channelId1 = channelRepository.getChannelIdByAuthorIdAndRecipientId(author, recipient);
        Integer channelId2 = channelRepository.getChannelIdByAuthorIdAndRecipientId(recipient, author);
        if(channelId1==null || channelId2==null) {
            return new ArrayList<>();
        }
        else {
            Channel channel1 = new Channel(channelId1);
            Channel channel2 = new Channel(channelId2);
            return chatRepository.getChatHistoryByPage(channel1, channel2, page);
        }
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void redis2Mysql() {
        log.info("[Chat Service] : scheduled task");
        Random random = new Random();
        Long randomLong = random.nextLong();
        if(tryLock(randomLong)){
            String prefix = KeyUtil.ChatKeyPrefix();
            List<String> keys = redisService.getRedisKeys(prefix);
            for (String chatKey : keys){
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
            unlock(randomLong);
        }
    }

    public boolean tryLock(Long random){
        if(redisService.setIfAbsent("lock", random, 300000L, TimeUnit.MILLISECONDS)){
            log.info("lock " + random);
            return true;
        }
        else{
            return false;
        }
    }

    public void unlock(Long randomLong){
        Long gotLock = (Long) redisService.get("lock");
        if(gotLock!=null && gotLock.longValue()==randomLong.longValue()){
            redisService.remove("lock");
            log.info("unlock " + randomLong);
        }
    }
}
