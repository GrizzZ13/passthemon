package com.backend.passthemon.service;

import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Chat;

import java.util.List;

public interface ChatService {
    void save(Chat chat);
    <S extends Chat> List<S> saveAll(Iterable<S> entities);
    void saveInRedis(ChatDto chatDto);
    List<Chat> getChatHistoryByChannelsAndOffset(Integer author, Integer recipient, Integer counts, Integer pageNumber);
}
