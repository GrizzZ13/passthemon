package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.HistoryDao;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {
    @Autowired
    private HistoryDao historyDao;

    @Override
    public Integer addHistory(User user, Goods goods){
        try {
            History history = new History();
            history.setGoods(goods);
            history.setUser(user);
            historyDao.saveAndFlush(history);
        }
        catch (Exception e){
            System.out.println("浏览历史添加错误！");
            return -1;
        }
        return 0;
    }

    @Override
    public List<History> listHistory(User user, Integer fetchPage){
        Pageable pageable = PageRequest.of(fetchPage, 15, Sort.Direction.DESC, "time");
        Page<History> page = historyDao.findAllHistoryByUser(user, pageable);
        List<History> result = page.getContent();

        return result;
    }

    @Override
    public List<History> listHistoryByTime(User user, Integer fetchPage, Timestamp startTime, Timestamp endTime){
        Pageable pageable = PageRequest.of(fetchPage, 15, Sort.Direction.DESC, "time");
        Page<History> page = historyDao.findAllHistoryByUserAndTimeBetween(user, pageable, startTime, endTime);
        List<History> result = page.getContent();

        return result;
    }

    @Override
    public History findHistoryByUserAndGoods(User user, Goods goods){
        return historyDao.findHistoryByUserAndGoods(user, goods);
    }

    @Override
    public History updateHistory(History history){
        long time = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(time);
        history.setTime(ts);
        History result = historyDao.saveAndFlush(history);

        return result;
    }

    @Override
    public Integer deleteAllHistory(User user){
        return historyDao.deleteAllByUser(user);
    }
}
