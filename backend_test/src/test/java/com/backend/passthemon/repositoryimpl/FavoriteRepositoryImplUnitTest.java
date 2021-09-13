package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.FavoriteDao;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

public class FavoriteRepositoryImplUnitTest {
    @Mock
    private FavoriteDao favoriteDao;

    @InjectMocks
    private FavoriteRepositoryImpl favoriteRepository;

    private User user = new User(1);

    private Goods goods = new Goods(1);

    private Favorite favorite = new Favorite();

    private List<Favorite> favoriteList = new ArrayList<>();

    private Page<Favorite> favoritePage = new Page<Favorite>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super Favorite, ? extends U> converter) {
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
        public List<Favorite> getContent() {
            return favoriteList;
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
        public Iterator<Favorite> iterator() {
            return null;
        }
    };

    Pageable pageable = PageRequest.of(0, 8);

    @Before
    public void setUp() throws Exception {
        favorite.setUser(user);
        favorite.setGoods(goods);
        MockitoAnnotations.openMocks(this);
        Mockito.when(favoriteDao.findAllFavoriteByUser(user, pageable)).thenReturn(favoritePage);
        Mockito.when(favoriteDao.save(new Favorite())).thenReturn(favorite);
        Mockito.when(favoriteDao.findFavoriteByGoodsAndUser(goods, user)).thenReturn(favorite);
    }

    @Test
    public void listFavoriteByPage() {
        List<Favorite> favoriteLists = favoriteRepository.listFavoriteByPage(0, user);
        Assert.assertEquals(favoriteLists, favoriteList);
        Mockito.verify(favoriteDao).findAllFavoriteByUser(user,pageable);
    }

    @Test
    public void addFavorite() {
        Favorite result = favoriteRepository.addFavorite(user, goods);
        Assert.assertNull(result);
        Mockito.verify(favoriteDao).save(favorite);
    }

    @Test
    public void deleteFavorite() {
        Assert.assertNull(favoriteRepository.deleteFavorite(user, goods));
        Mockito.verify(favoriteDao).deleteByGoodsAndUser(goods, user);
    }

    @Test
    public void deleteAllFavorite() {
        Assert.assertNull(favoriteRepository.deleteAllFavorite(user));
        Mockito.verify(favoriteDao).deleteByUser(user);
    }

    @Test
    public void checkFavorite() {
        Favorite result = favoriteRepository.checkFavorite(user, goods);
        Assert.assertEquals(favorite, result);
        Mockito.verify(favoriteDao).findFavoriteByGoodsAndUser(goods, user);
    }
}
