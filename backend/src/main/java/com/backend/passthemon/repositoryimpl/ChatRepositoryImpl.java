package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.config.OffsetBasedPageRequest;
import com.backend.passthemon.dao.jpa.ChatDao;
import com.backend.passthemon.dto.ChatDto;
import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import com.backend.passthemon.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepositoryImpl implements ChatRepository {
    @Autowired
    private ChatDao chatDao;

    @Override
    public void save(Chat chat) {
        chatDao.saveAndFlush(chat);
    }

    @Override
    public <S extends Chat> List<S> saveAll(Iterable<S> entities) {
        return chatDao.saveAll(entities);
    }

    @Override
    public List<Chat> getChatHistoryByChannelsAndOffset(Channel channel1, Channel channel2, Integer counts, Integer pageNumber) {
        Integer limit = 20;
        Integer offset = limit * pageNumber + counts;
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(offset, limit, sort);
        Page<Chat> chatPage = chatDao.findAllByChannelEqualsOrChannelEquals(channel1, channel2, pageRequest);
        return chatPage.getContent();
    }

    @Override
    public List<Chat> getChatHistoryByPage(Channel channel1, Channel channel2, Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        PageRequest pageRequest = PageRequest.of(page, 20, sort);
        Page<Chat> chatPage = chatDao.findAllByChannelEqualsOrChannelEquals(channel1, channel2, pageRequest);
        return chatPage.getContent();
    }
}
