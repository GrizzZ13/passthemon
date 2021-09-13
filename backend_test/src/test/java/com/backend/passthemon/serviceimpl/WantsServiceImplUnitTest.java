package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.entity.Wants;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.repository.WantsRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class WantsServiceImplUnitTest {
    @Mock
    private WantsRepository wantsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private WantsServiceImpl wantsService;

    private Integer userId = 1, goodsId = 1, fetchPage = 1;

    private User user = new User(userId);

    private Goods goods = new Goods(goodsId);

    private Wants wants;

    private List<Wants> wantsList = new ArrayList<>();

    private Integer buyerId = 2, sellerId = 3, num = 4, wantsId = 5;

    private User buyer = new User(buyerId);

    private User seller = new User(sellerId);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(wantsRepository.checkWants(user, goods)).thenReturn(wants);
        Mockito.when(userRepository.findUserById(buyerId)).thenReturn(buyer);
        Mockito.when(userRepository.findUserById(sellerId)).thenReturn(seller);
        Mockito.when(goodsRepository.findGoodsById(goodsId)).thenReturn(goods);
        Mockito.when(wantsRepository.addWants(buyer, seller, goods, num)).thenReturn(wantsId);
        Mockito.when(wantsRepository.deleteWants(buyer, seller, goods)).thenReturn(0);
        Mockito.when(wantsRepository.sellerListWants(seller, fetchPage)).thenReturn(wantsList);
    }

    @Test
    void addWants() {
        Integer result = wantsService.addWants(buyerId, sellerId, goodsId, num);
        Assert.assertEquals(result.toString(), String.valueOf(wantsId));
        Mockito.verify(userRepository).findUserById(buyerId);
        Mockito.verify(userRepository).findUserById(sellerId);
        Mockito.verify(goodsRepository).findGoodsById(goodsId);
        Mockito.verify(wantsRepository).addWants(buyer, seller, goods, num);
    }

    @Test
    void deleteWants() {
        Integer result = wantsService.deleteWants(buyerId, sellerId, goodsId);
        Assert.assertEquals(result.toString(), String.valueOf(0));
        Mockito.verify(wantsRepository).deleteWants(buyer, seller, goods);
    }

    @Test
    void sellerListWants() {
        List<Wants> result = wantsService.sellerListWants(sellerId, fetchPage);
        Assert.assertEquals(result.toString(), wantsList.toString());
        Mockito.verify(wantsRepository).sellerListWants(seller, fetchPage);
    }
    @Test
    void checkWants() {
        Wants result = wantsService.checkWants(1, 1);
        Assert.assertEquals(wants, result);
        Mockito.verify(wantsRepository).checkWants(user, goods);
    }
}
