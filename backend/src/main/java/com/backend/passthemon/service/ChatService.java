package com.backend.passthemon.service;

import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Chat;

import java.util.List;

public interface ChatService {
    @Deprecated
    List<Chat> getChatHistoryByChannelsAndOffset(Integer author, Integer recipient, Integer counts, Integer pageNumber);

    void save(Chat chat);
    <S extends Chat> List<S> saveAll(Iterable<S> entities);
    void saveInRedis(ChatDto chatDto);
    List<Chat> getChatHistoryByPage(Integer author, Integer recipient, Integer page);
}
