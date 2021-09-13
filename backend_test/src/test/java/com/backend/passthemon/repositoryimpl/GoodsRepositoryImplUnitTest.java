package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.GoodsDao;
import com.backend.passthemon.dao.ImagesDao;
import com.backend.passthemon.dto.GoodInfoDto;
import com.backend.passthemon.entity.Goods;
import com.backend.passthemon.entity.Images;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repositoryimpl.GoodsRepositoryImpl;
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

import static com.backend.passthemon.constant.GoodsConstant.*;
import static com.backend.passthemon.constant.GoodsConstant.BEING_AUDITED;
import static org.junit.jupiter.api.Assertions.*;

class GoodsRepositoryImplUnitTest {
    @Mock
    private GoodsDao goodsDao;
    @Mock
    private ImagesDao imagesDao;

    @InjectMocks
    private GoodsRepositoryImpl goodsRepository;

    private User user = new User(1);

    private Goods goods = new Goods(1);

    private GoodInfoDto goodInfoDto=new GoodInfoDto(goods);
    private List<Goods> goodsList = new ArrayList<>();

    private List<Integer> stateList = new ArrayList<>();

    private String search = "1";

    private Page<Goods> goodsPage = new Page<Goods>() {
        @Override
        public int getTotalPages() {
            return 1;
        }

        @Override
        public long getTotalElements() {
            return 1;
        }

        @Override
        public <U> Page<U> map(Function<? super Goods, ? extends U> function) {
            return null;
        }

        @Override
        public int getNumber() {
            return 1;
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

    private Integer category = 0, attrition = 0, state = 1, fetchPage = 1;

    Pageable pageable = PageRequest.of(0, 8);

    @BeforeEach
    void setUp() {
        stateList.add(IMAGE_FAILED_AUDIT);
        stateList.add(TEXT_FAILED_AUDIT);
        stateList.add(ON_SALE);
        stateList.add(BEING_AUDITED);
        goodsList.add(goods);
        MockitoAnnotations.openMocks(this);
        Mockito.when(goodsDao.findAllByState(1, pageable)).thenReturn(goodsPage);
        Mockito.when(goodsDao.findAllByUserAndStateIn(pageable, user, stateList)).thenReturn(goodsPage);
        Mockito.when(goodsDao.findAllByNameLikeAndState("%"+search+"%", pageable, 1)).thenReturn(goodsPage);
        Mockito.when(goodsDao.findGoodsById(1)).thenReturn(goods);
        Mockito.when(goodsDao.saveAndFlush(goods)).thenReturn(goods);
    }

    @Test
    void listGoodsByPages() {
        category = 0; attrition = 0; state = 1;
        Pageable pageable = PageRequest.of(fetchPage, 8);
        Mockito.when(goodsDao.findAllByState(state, pageable)).thenReturn(goodsPage);
        Assert.assertEquals(goodsRepository.listGoodsByPages(fetchPage, category, attrition), goodsPage);
        Mockito.verify(goodsDao).findAllByState(1, pageable);

        category = 1; attrition = 0;
        Mockito.when(goodsDao.findAllByStateAndCategory(state, category, pageable)).thenReturn(goodsPage);
        Assert.assertEquals(goodsRepository.listGoodsByPages(fetchPage, category, attrition), goodsPage);
        Mockito.verify(goodsDao).findAllByStateAndCategory(state, category, pageable);

        category = 0; attrition = 1;
        Mockito.when(goodsDao.findAllByStateAndAttrition(pageable, state, attrition)).thenReturn(goodsPage);
        Assert.assertEquals(goodsRepository.listGoodsByPages(fetchPage, category, attrition), goodsPage);
        Mockito.verify(goodsDao).findAllByStateAndAttrition(pageable, state, attrition);

        category = 1; attrition = 1;
        Mockito.when(goodsDao.findAllByStateAndAttritionAndCategory(pageable, state, attrition, category)).thenReturn(goodsPage);
        Assert.assertEquals(goodsRepository.listGoodsByPages(fetchPage, category, attrition), goodsPage);
        Mockito.verify(goodsDao).findAllByStateAndAttritionAndCategory(pageable, state, attrition, category);
    }

    @Test
    void listMyGoodsByPages() {
        Integer fetchPage = 0;
        List<Goods> result = goodsRepository.listMyGoodsByPages(fetchPage, user);
        Assert.assertEquals(goodsList, result);
        Mockito.verify(goodsDao).findAllByUserAndStateIn(pageable, user, stateList);
    }

    @Test
    void searchGoods() {
        List<Goods> list = goodsRepository.searchGoods(search, 0);
        Assert.assertEquals(goodsList, list);
        Mockito.verify(goodsDao).findAllByNameLikeAndState("%"+search+"%", pageable, 1);
    }

    @Test
    void findGoodsById() {
        Goods result = goodsRepository.findGoodsById(1);
        Assert.assertEquals(result, goods);
        Mockito.verify(goodsDao).findGoodsById(1);
    }

    @Test
    public void addGood() {
        Integer result=goodsRepository.addGood(goods);
        Assert.assertEquals(result.toString(),"1");
        Mockito.verify(goodsDao).saveAndFlush(goods);
    }

    @Test
    void editGood() {
        List<String> imagesList = new ArrayList<>();
        String images = "images";
        imagesList.add(images);
        goodInfoDto.setImages(imagesList);

        goodsRepository.editGood(goodInfoDto);
        Mockito.verify(imagesDao).deleteAllByGoodsId(1);
        Mockito.verify(goodsDao).saveAndFlush(goods);
    }

    @Test
    void removeGoods() {
        goodsRepository.removeGoods(1);
        Mockito.verify(goodsDao).findGoodsById(1);
        Mockito.verify(goodsDao).saveAndFlush(goods);
    }

    @Test
    void changeState() {
        goodsRepository.changeState(0,1);
        Mockito.verify(goodsDao).findGoodsById(1);
        Mockito.verify(goodsDao).saveAndFlush(goods);
    }

    @Test
    void getMaxGoodsPage() {
        category = 0; attrition = 0;
        int result = goodsRepository.getMaxGoodsPage(category, attrition);
        Assert.assertEquals(result,1);
        Mockito.verify(goodsDao).findAllByState(state, pageable);

        category = 1; attrition = 0;
        Mockito.when(goodsDao.findAllByStateAndCategory(state, category, pageable)).thenReturn(goodsPage);
        goodsRepository.getMaxGoodsPage(category, attrition);
        Mockito.verify(goodsDao).findAllByStateAndCategory(state, category, pageable);

        category = 0; attrition = 1;
        Mockito.when(goodsDao.findAllByStateAndAttrition(pageable, state, attrition)).thenReturn(goodsPage);
        goodsRepository.getMaxGoodsPage(category, attrition);
        Mockito.verify(goodsDao).findAllByStateAndAttrition(pageable, state, attrition);

        category = 1; attrition = 1;
        Mockito.when(goodsDao.findAllByStateAndAttritionAndCategory(pageable, state, attrition, category)).thenReturn(goodsPage);
        goodsRepository.getMaxGoodsPage(category, attrition);
        Mockito.verify(goodsDao).findAllByStateAndAttritionAndCategory(pageable, state, attrition, category);

    }
}
