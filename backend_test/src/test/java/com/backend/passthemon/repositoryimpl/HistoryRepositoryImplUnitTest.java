package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.HistoryDao;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.History;
import com.backend.passthemon.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class HistoryRepositoryImplUnitTest {
    @Mock
    private HistoryDao historyDao;

    @InjectMocks
    private HistoryRepositoryImpl historyRepository;

    private final User user = new User(1);

    private final Goods goods = new Goods(1);

    private final History history = new History();

    private final List<History> historyList = new ArrayList<>();

    private final Pageable pageable = PageRequest.of(0, 15, Sort.Direction.DESC, "time");

    private final Timestamp startTime = Timestamp.valueOf("2021-05-06 00:00:00");

    private final Timestamp endTime = Timestamp.valueOf("2021-08-26 23:59:59");

    private final Page<History> historyPage = new Page<History>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super History, ? extends U> function) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public int getNumberOfElements() {
            return 0;
        }

        @Override
        public List<History> getContent() {
            return historyList;
        }

        @Override
        public boolean hasContent() {
            return false;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return false;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator<History> iterator() {
            return null;
        }
    };

    private final RuntimeException runtimeException = new RuntimeException();

    @BeforeEach
    void setUp() {
        history.setUser(user);
        history.setGoods(goods);
        MockitoAnnotations.openMocks(this);
        Mockito.when(historyDao.save(history)).thenReturn(history);
        Mockito.when(historyDao.findAllHistoryByUser(user, pageable)).thenReturn(historyPage);
        Mockito.when(historyDao.findAllHistoryByUserAndTimeBetween(user, pageable, startTime, endTime)).thenReturn(historyPage);
        Mockito.when(historyDao.findHistoryByUserAndGoods(user, goods)).thenReturn(history);
        Mockito.when(historyDao.deleteAllByUser(user)).thenReturn(1);
    }

    @Test
    void addHistory() {
        int result = historyRepository.addHistory(user, goods);
        Assert.assertEquals(0, result);
        Mockito.verify(historyDao).save(history);

        Mockito.when(historyDao.save(history)).thenThrow(runtimeException);
        result = historyRepository.addHistory(user, goods);
        Assert.assertEquals(-1, result);
    }

    @Test
    void listHistory() {
        List<History> result = historyRepository.listHistory(user, 0);
        Assert.assertEquals(historyList, result);
        Mockito.verify(historyDao).findAllHistoryByUser(user, pageable);
    }

    @Test
    void listHistoryByTime() {
        List<History> result = historyRepository.listHistoryByTime(user, 0, startTime, endTime);
        Assert.assertEquals(historyList, result);
        Mockito.verify(historyDao).findAllHistoryByUserAndTimeBetween(user, pageable, startTime, endTime);
    }

    @Test
    void findHistoryByUserAndGoods() {
        History result = historyRepository.findHistoryByUserAndGoods(user, goods);
        Assert.assertEquals(history, result);
        Mockito.verify(historyDao).findHistoryByUserAndGoods(user, goods);
    }

    @Test
    void updateHistory() {
        History result = historyRepository.updateHistory(history);
        Assert.assertEquals(history, result);
        Mockito.verify(historyDao).save(history);
    }

    @Test
    void deleteAllHistory() {
        int result = historyRepository.deleteAllHistory(user);
        Assert.assertEquals(1, result);
        Mockito.verify(historyDao).deleteAllByUser(user);
    }
}
