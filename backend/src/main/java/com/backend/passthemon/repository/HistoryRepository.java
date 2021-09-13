package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryRepository {
    Integer addHistory(User user, Goods goods);
    List<History> listHistory(User user, Integer fetchPage);
    List<History> listHistoryByTime(User user, Integer fetchPage, Timestamp startTime, Timestamp endTime);
    History findHistoryByUserAndGoods(User user, Goods goods);
    History updateHistory(History history);
    Integer deleteAllHistory(User user);
}
