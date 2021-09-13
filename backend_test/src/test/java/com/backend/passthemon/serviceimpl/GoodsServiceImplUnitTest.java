package com.backend.passthemon.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.dto.GoodsDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.GoodsRepository;
import com.backend.passthemon.repository.HistoryRepository;
import com.backend.passthemon.repository.ImagesRepository;
import com.backend.passthemon.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

class GoodsServiceImplUnitTest {
    @Mock
    private GoodsRepository goodsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImagesRepository imagesRepository;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private GoodsServiceImpl goodsService;

    private List<Goods> goodsList = new ArrayList<>();

    private List<GoodsDto> goodsDtoList = new ArrayList<>();

    private JSONObject json = new JSONObject();

    private Page<Goods> goodsPage = new Page<Goods>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super Goods, ? extends U> function) {
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
        public List<Goods> getContent() {
            return goodsList;
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
        public Iterator<Goods> iterator() {
            return null;
        }
    };

    private Goods goods = new Goods(1);

    private User user = new User(1);

    private GoodInfoDto goodInfoDto=new GoodInfoDto();

    @BeforeEach
    void setUp() throws Exception {
        goods.setUser(user);
        goodsList.add(goods);
        goodsDtoList = GoodsDto.convert(goodsList);
        json.put("list", goodsDtoList);
        json.put("maxPage", 0);
        json.put("currentPage", 0);
        MockitoAnnotations.initMocks(this);
        Mockito.when(goodsRepository.listGoodsByPages(0, 0, 0)).thenReturn(goodsPage);
        Mockito.when(goodsRepository.listGoodsByPages(0, 0, 0)).thenReturn(goodsPage);
        Mockito.when(goodsRepository.listMyGoodsByPages(0, user)).thenReturn(goodsList);
        Mockito.when(goodsRepository.findGoodsById(1)).thenReturn(goods);
        Mockito.when(goodsRepository.getMaxGoodsPage(0, 0)).thenReturn(0);
        Mockito.when(goodsRepository.searchGoods("", 0)).thenReturn(goodsList);
        Mockito.when(goodsRepository.addGood(goods)).thenReturn(1);
        Mockito.when(goodsRepository.findGoodsById(1)).thenReturn(goods);
        Mockito.when(goodsRepository.listGoodsByPages(1,1,-1)).thenReturn(goodsPage);
        Mockito.when(goodsRepository.listMyGoodsByPages(0,user)).thenReturn(goodsList);
        Mockito.when(goodsRepository.searchGoods("",0)).thenReturn(goodsList);
        Mockito.when(goodsRepository.getMaxGoodsPage(0,-1)).thenReturn(4);
    }

    @Test
    void listGoodsByPages() {
        JSONObject jsonObject = goodsService.listGoodsByPages(0, 1, 0, 0);
        Assert.assertEquals(json, jsonObject);
        Mockito.verify(goodsRepository).listGoodsByPages(0, 0, 0);
    }

    @Test
    void listMyGoodsByPages() {
        List<GoodsDto> list = goodsService.listMyGoodsByPages(0, 1);
        Assert.assertEquals(goodsDtoList, list);
        Mockito.verify(goodsRepository).listMyGoodsByPages(0, user);
    }

    @Test
    void searchGoods() {
        List<Goods> list = goodsService.searchGoods("", 0);
        Assert.assertEquals(goodsList, list);
        Mockito.verify(goodsRepository).searchGoods("", 0);
    }

    @Test
    void findGoodsById() {
        Goods result = goodsService.findGoodsById(1);
        Assert.assertEquals(result,goods);
        Mockito.verify(goodsRepository).findGoodsById(1);
    }

    @Test
    public void addGood() {
        Integer result=goodsService.addGood(goods);
        Assert.assertEquals(result.toString(),"1");
        Mockito.verify(goodsRepository).addGood(goods);
    }

    @Test
    public void editGood() {
        Mockito.doNothing().when(goodsRepository).editGood(Mockito.any());
        goodsService.editGood(goodInfoDto);
        Mockito.verify(goodsRepository,Mockito.times(1)).editGood(goodInfoDto);
    }

    @Test
    public void removeGoods() {
        Mockito.doNothing().when(goodsRepository).removeGoods(Mockito.anyInt());
        goodsService.removeGoods(1);
        Mockito.verify(goodsRepository,Mockito.times(1)).removeGoods(1);
    }

    @Test
    public void changeState() {
        Mockito.doNothing().when(goodsRepository).changeState(Mockito.anyInt(),Mockito.anyInt());
        goodsService.changeState(1,1);
        Mockito.verify(goodsRepository,Mockito.times(1)).changeState(1,1);
    }

    @Test
    void getMaxGoodsPage() {
        int result = goodsService.getMaxGoodsPage(0, 0);
        Assert.assertEquals(result,0);
        Mockito.verify(goodsRepository).getMaxGoodsPage(0, 0);
    }
}
