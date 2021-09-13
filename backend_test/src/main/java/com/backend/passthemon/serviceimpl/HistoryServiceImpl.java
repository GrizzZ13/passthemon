package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.HistoryRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public Integer addHistory(Integer userId, int goodsId){
        User user = new User(userId);
        Goods goods = new Goods(goodsId);
        History history = historyRepository.findHistoryByUserAndGoods(user, goods);
        if(history != null){
            historyRepository.updateHistory(history);
            return 1;
        }
        else {
            return historyRepository.addHistory(user, goods);
        }
    }

    @Override
    public List<History> listHistory(Integer userId, Integer fetchPage){
        User user = new User(userId);

        return historyRepository.listHistory(user, fetchPage);
    }

    @Override
    public List<History> listHistoryByTime(Integer userId, Integer fetchPage, Timestamp startTime, Timestamp endTime){
        User user = new User(userId);

        return historyRepository.listHistoryByTime(user, fetchPage, startTime, endTime);
    }

    @Override
    public Integer deleteAllHistory(Integer userId){
        User user = new User(userId);

        return historyRepository.deleteAllHistory(user);
    }
}
