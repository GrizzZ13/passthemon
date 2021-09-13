package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.HistoryRepository;
import com.backend.passthemon.repositoryimpl.HistoryRepositoryImpl;
import com.backend.passthemon.serviceimpl.HistoryServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceImplUnitTest {
    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryServiceImpl historyService;

    private Integer userId = 1, goodsId = 1;

    private User user = new User(userId);

    private Goods goods = new Goods(goodsId);

    private List<History> historyList = new ArrayList<>();

    private History history = new History();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(historyRepository.listHistory(user, 0)).thenReturn(historyList);
    }

    @Test
    void addHistory() {
        Mockito.when(historyRepository.findHistoryByUserAndGoods(user, goods)).thenReturn(history);
        int result = historyService.addHistory(userId, goodsId);
        Assert.assertEquals(1, result);     //检查结果
        Mockito.verify(historyRepository).updateHistory(history);

        Mockito.when(historyRepository.findHistoryByUserAndGoods(user, goods)).thenReturn(null);
        result = historyService.addHistory(userId, goodsId);
        Assert.assertEquals(0, result);
        Mockito.verify(historyRepository).addHistory(user, goods);
    }

    @Test
    void listHistory() {
        List<History> result = historyService.listHistory(1, 0);
        Assert.assertEquals(historyList, result);     //检查结果
        Mockito.verify(historyRepository).listHistory(user, 0);
    }

    @Test
    void listHistoryByTime() {
        Timestamp startTime = Timestamp.valueOf("2021-05-06 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2021-08-26 23:59:59");
        List<History> result = historyService.listHistoryByTime(1, 0, startTime, endTime);
        Assert.assertEquals(historyList, result);     //检查结果
        Mockito.verify(historyRepository).listHistoryByTime(user, 0, startTime, endTime);
    }

    @Test
    void deleteAllHistory() {
        int result = historyService.deleteAllHistory(1);
        Assert.assertEquals(0, result);
        Mockito.verify(historyRepository).deleteAllHistory(user);
    }
}
