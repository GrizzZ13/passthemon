package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.WantsDao;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class WantsRepositoryImplUnitTest {
    @Mock
    private WantsDao wantsDao;

    @InjectMocks
    private WantsRepositoryImpl wantsRepository;

    private Integer goodsId = 1, wantsId = 1, userId = 1, buyerId = 2, sellerId = 3, num = 1;

    private Goods goods = new Goods(goodsId);

    private User user = new User(userId);

    private Wants wants = new Wants();

    private User buyer = new User(buyerId), seller = new User(sellerId);

    private Integer fetchPage = 1;

    private List<Wants> wantsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(wantsDao.findWantsByBuyerAndGoods(user, goods)).thenReturn(wants);
    }

    @Test
    void addWants() {
        wants.setId(null);
        wants.setBuyer(buyer);
        wants.setSeller(seller);
        wants.setGoods(goods);
        wants.setNum(num);
        Mockito.when(wantsDao.save(wants)).thenReturn(wants);
        wantsRepository.addWants(buyer, seller, goods, num);
        Mockito.verify(wantsDao).save(wants);
    }

    @Test
    void deleteWants() {
        Mockito.when(wantsDao.deleteWantsByBuyerAndGoodsAndSeller(buyer, goods, seller)).thenReturn(0);
        Assert.assertEquals(wantsRepository.deleteWants(buyer, seller, goods).toString(),
                String.valueOf(0));
        Mockito.verify(wantsDao).deleteWantsByBuyerAndGoodsAndSeller(buyer, goods, seller);
    }

    @Test
    void sellerListWants() {
        Pageable pageable = PageRequest.of(fetchPage, 8);
        Page<Wants> wantsPage = new Page<Wants>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Wants, ? extends U> converter) {
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
            public List<Wants> getContent() {
                return wantsList;
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
            public Iterator<Wants> iterator() {
                return null;
            }
        };
        Mockito.when(wantsDao.findAllBySeller(seller, pageable)).thenReturn(wantsPage);
        Assert.assertEquals(wantsRepository.sellerListWants(seller, fetchPage).toString(),
                wantsList.toString());
        Mockito.verify(wantsDao).findAllBySeller(seller, pageable);
    }

    @Test
    void checkWants() {
        Wants result = wantsRepository.checkWants(user, goods);
        Assert.assertEquals(wants, result);
        Mockito.verify(wantsDao).findWantsByBuyerAndGoods(user, goods);
    }
}
