package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;

import java.util.List;

public interface ChatRepository {
    void save(Chat chat);
    <S extends Chat> List<S> saveAll(Iterable<S> entities);
    List<Chat> getChatHistoryByChannelsAndOffset(Channel channel1, Channel channel2, Integer counts, Integer pageNumber);
}
