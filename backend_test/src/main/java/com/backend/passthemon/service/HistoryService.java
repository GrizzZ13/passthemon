package com.backend.passthemon.service;

import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryService {
    Integer addHistory(Integer userId, int goodsId);
    List<History> listHistory(Integer userId, Integer fetchPage);
    List<History> listHistoryByTime(Integer userId, Integer fetchPage, Timestamp startTime, Timestamp endTime);
    Integer deleteAllHistory(Integer userId);
}
