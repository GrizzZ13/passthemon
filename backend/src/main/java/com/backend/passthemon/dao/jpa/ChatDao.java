package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Channel;
import com.backend.passthemon.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatDao extends JpaRepository<Chat, Long> {
    Page<Chat> findAllByChannelEqualsOrChannelEquals(Channel channel, Channel channel2, Pageable pageable);
}
