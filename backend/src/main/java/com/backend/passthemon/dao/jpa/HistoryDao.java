package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryDao extends JpaRepository<History,Integer> {
    Page<History> findAllHistoryByUser(User user, Pageable pageable);

    Page<History> findAllHistoryByUserAndTimeBetween(User user, Pageable pageable, Timestamp startTime, Timestamp endTime);

    History findHistoryByUserAndGoods(User user, Goods goods);

    @Modifying
    @Transactional
    Integer deleteAllByUser(User user);
}
