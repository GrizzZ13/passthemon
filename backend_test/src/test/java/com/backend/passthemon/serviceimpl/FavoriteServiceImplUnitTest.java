package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.FavoriteDto;
import com.backend.passthemon.entity.Favorite;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.FavoriteRepository;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.serviceimpl.FavoriteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImplUnitTest {
    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private List<Favorite> favoriteList = new ArrayList<>();

    private Favorite favorite = new Favorite();

    private User user = new User(1);

    private Goods goods = new Goods(1);

    @Before
    public void setUp() throws Exception {
        favorite.setGoods(goods);
        favorite.setUser(user);
        MockitoAnnotations.initMocks(this);
        Mockito.when(favoriteRepository.listFavoriteByPage(0, user)).thenReturn(favoriteList);
        Mockito.when(favoriteRepository.addFavorite(user,goods)).thenReturn(favorite);
        Mockito.when(favoriteRepository.checkFavorite(user,goods)).thenReturn(favorite);
        Mockito.when(userRepository.findUserById(1)).thenReturn(user);
        Mockito.when(goodsRepository.findGoodsById(1)).thenReturn(goods);
        Mockito.when(favoriteRepository.deleteFavorite(user, goods)).thenReturn(favorite);
        Mockito.when(favoriteRepository.deleteAllFavorite(user)).thenReturn(favorite);
    }

    @Test
    public void listFavoriteByPage() {
        List<FavoriteDto> favoriteDtoList = favoriteService.listFavoriteByPage(0, 1);
        Assert.assertEquals(favoriteDtoList, FavoriteDto.convert(favoriteList));
        Mockito.verify(favoriteRepository).listFavoriteByPage(0, user);
    }

    @Test
    public void addFavorite() {
        Favorite result = favoriteService.addFavorite(1, 1);
        Assert.assertEquals(favorite, result);
        Mockito.verify(favoriteRepository).addFavorite(user,goods);
    }

    @Test
    public void deleteFavorite() {
        Assert.assertEquals(favoriteService.deleteFavorite(user.getId(), goods.getId()), favorite);
        Mockito.verify(favoriteRepository).deleteFavorite(user, goods);
    }

    @Test
    public void deleteAllFavorite() {
        Assert.assertEquals(favoriteService.deleteAllFavorite(user.getId()), favorite);
        Mockito.verify(favoriteRepository).deleteAllFavorite(user);
    }

    @Test
    public void checkFavorite() {
        Favorite result = favoriteService.checkFavorite(1, 1);
        Assert.assertEquals(favorite, result);
        Mockito.verify(favoriteRepository).checkFavorite(user,goods);
    }
}
