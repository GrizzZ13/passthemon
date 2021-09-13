package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.DemandDao;
import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static com.backend.passthemon.constant.GoodsConstant.*;
import static com.backend.passthemon.constant.GoodsConstant.BEING_AUDITED;

class DemandRepositoryImplUnitTest {
    @Mock
    DemandDao demandDao;

    @InjectMocks
    private DemandRepositoryImpl demandRepository;

    private final Demand demand = new Demand();

    private DemandInfoDto demandInfoDto=new DemandInfoDto();

    private User user = new User(1);

    private List<Demand> demandList = new ArrayList<>();

    private Page<Demand> demandPage = new Page<Demand>() {
        @Override
        public int getTotalPages() {
            return 0;
        }

        @Override
        public long getTotalElements() {
            return 0;
        }

        @Override
        public <U> Page<U> map(Function<? super Demand, ? extends U> converter) {
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
        public List<Demand> getContent() {
            return demandList;
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
        public Iterator<Demand> iterator() {
            return null;
        }
    };

    private Pageable pageable = PageRequest.of(0, 8, Sort.Direction.DESC, "upLoadTime");

    private Integer demandId = 1;
    private Integer fetchPage = 1, category = 0, attrition = 0, state = 1;

    @BeforeEach
    void setUp() {
        demand.setId(1);
        demand.setName("test1");
        demandList.add(demand);
        demandInfoDto.setDemandId(1);
        MockitoAnnotations.openMocks(this);
        Mockito.when(demandDao.findAllByState(1, pageable)).thenReturn(demandPage);
        Mockito.when(demandDao.getOne(1)).thenReturn(demand);
        Mockito.when(demandDao.findDemandById(1)).thenReturn(demand);
        Mockito.when(demandDao.saveAndFlush(demand)).thenReturn(demand);
    }

    @Test
    void addDemand() {
        Integer result=demandRepository.addDemand(demand);
        Assert.assertEquals(result.toString(),"1");
        Mockito.verify(demandDao).saveAndFlush(demand);
    }

    @Test
    void listAllDemandsByPage() {
        Pageable pageable = PageRequest.of(fetchPage, 8, Sort.Direction.DESC, "upLoadTime");

        category = 0; attrition = 0; state = 1;
        Mockito.when(demandDao.findAllByState(state, pageable)).thenReturn(demandPage);
        Assert.assertEquals(demandRepository.listAllDemandsByPage(fetchPage, category, attrition).toString(), demandPage.getContent().toString());   //检查结果
        Mockito.verify(demandDao).findAllByState(state, pageable);       //验证是否调用过demandRepository.listAllDemandsByPage(0)

        category = 1; attrition = 0;
        Mockito.when(demandDao.findAllByStateAndCategory(state, category, pageable)).thenReturn(demandPage);
        Assert.assertEquals(demandRepository.listAllDemandsByPage(fetchPage, category, attrition).toString(), demandPage.getContent().toString());   //检查结果
        Mockito.verify(demandDao).findAllByStateAndCategory(state, category, pageable);       //验证是否调用过demandRepository.listAllDemandsByPage(0)

        category = 0; attrition = 1;
        Mockito.when(demandDao.findAllByStateAndAttrition(state, attrition, pageable)).thenReturn(demandPage);
        Assert.assertEquals(demandRepository.listAllDemandsByPage(fetchPage, category, attrition).toString(), demandPage.getContent().toString());   //检查结果
        Mockito.verify(demandDao).findAllByStateAndAttrition(state, attrition, pageable);       //验证是否调用过demandRepository.listAllDemandsByPage(0)

        category = 1; attrition = 1;
        Mockito.when(demandDao.findAllByStateAndAttritionAndCategory(state, attrition, category, pageable)).thenReturn(demandPage);
        Assert.assertEquals(demandRepository.listAllDemandsByPage(fetchPage, category, attrition).toString(), demandPage.getContent().toString());   //检查结果
        Mockito.verify(demandDao).findAllByStateAndAttritionAndCategory(state, attrition, category, pageable);       //验证是否调用过demandRepository.listAllDemandsByPage(0)
    }

    @Test
    void listOnesDemandsByPage() {
        Pageable pageable = PageRequest.of(fetchPage, 8, Sort.Direction.DESC, "upLoadTime");
        List<Integer> stateList = new ArrayList<>();
        stateList.add(IMAGE_FAILED_AUDIT);
        stateList.add(TEXT_FAILED_AUDIT);
        stateList.add(ON_SALE);
        stateList.add(BEING_AUDITED);
        Mockito.when(demandDao.findAllByUserAndStateIn(pageable, user, stateList)).thenReturn(demandPage);
        Assert.assertEquals(demandRepository.listOnesDemandsByPage(fetchPage, user).toString(), demandPage.getContent().toString());     //检查结果
        Mockito.verify(demandDao).findAllByUserAndStateIn(pageable, user, stateList);       //验证是否调用过demandRepository.listAllDemandsByPage(0)
    }

    @Test
    void getDemandById() {
        Mockito.when(demandDao.findDemandById(demandId)).thenReturn(demand);
        Assert.assertEquals(demandRepository.getDemandById(demandId).toString(), demand.toString());
        Mockito.verify(demandDao).findDemandById(demandId);
    }
    @Test
    void editDemand(){
        demandRepository.editDemand(demandInfoDto);
        Mockito.verify(demandDao).findDemandById(1);
        Mockito.verify(demandDao).saveAndFlush(demand);
    }
    @Test
    void removeDemand(){
        demandRepository.removeDemand(1);
        Mockito.verify(demandDao).findDemandById(1);
        Mockito.verify(demandDao).saveAndFlush(demand);
    }
    @Test
    void changeState(){
        demandRepository.changeState(1,0);
        Mockito.verify(demandDao).findDemandById(1);
        Mockito.verify(demandDao).saveAndFlush(demand);
    }

}
