package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.DemandRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.serviceimpl.DemandServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

class DemandServiceImplUnitTest {
    @Mock
    DemandRepository demandRepository;

    @InjectMocks
    private DemandServiceImpl demandService;

    private List<Demand> demandList = new ArrayList<>();

    private final Demand demand = new Demand();

    private User user = new User(1);

    private final DemandInfoDto demandInfoDto=new DemandInfoDto();

    @BeforeEach
    void setUp() {
        demand.setId(1);
        demand.setName("test1");
        demandList.add(demand);
        MockitoAnnotations.openMocks(this);
        Mockito.when(demandRepository.listAllDemandsByPage(0, 0, 0)).thenReturn(demandList);
        Mockito.when(demandRepository.listOnesDemandsByPage(0, user)).thenReturn(demandList);
        Mockito.when(demandRepository.getDemandById(1)).thenReturn(demand);
        Mockito.when(demandRepository.addDemand(demand)).thenReturn(1);
    }

    @Test
    void addDemand() {
        Integer demandId=demandService.addDemand(demand);
        Assert.assertEquals(demandId.toString(),"1");
        Mockito.verify(demandRepository).addDemand(demand);
    }
    @Test
    void removeDemand(){
        Mockito.doNothing().when(demandRepository).removeDemand(Mockito.anyInt());
        demandService.removeDemand(1);
        Mockito.verify(demandRepository,Mockito.times(1)).removeDemand(1);
    }

    @Test
    void listAllDemandsByPage() {
        List<Demand> demandList = demandService.listAllDemandsByPage(0, 0, 0);
        Assert.assertEquals(demandList.get(0).getName(), "test1");     //检查结果
        Mockito.verify(demandRepository).listAllDemandsByPage(0, 0, 0);       //验证是否调用过demandRepository.listAllDemandsByPage(0)
    }

    @Test
    void listOnesDemandsByPage() {
        List<Demand> demandList = demandService.listOnesDemandsByPage(0, 1);
        Assert.assertEquals(demandList.get(0).getName(), "test1");     //检查结果
        Mockito.verify(demandRepository).listOnesDemandsByPage(0, user);       //验证是否调用过demandRepository.listAllDemandsByPage(0)
    }

    @Test
    void getDemandById() {
        Demand result = demandService.getDemandById(1);
        Assert.assertEquals(result.getName(),"test1");
        Mockito.verify(demandRepository).getDemandById(1);
    }
    @Test
    void changeState(){
        Mockito.doNothing().when(demandRepository).changeState(Mockito.anyInt(),Mockito.anyInt());
        demandService.changeState(1,2);
        Mockito.verify(demandRepository,Mockito.times(1)).changeState(1,2);
    }

    @Test
    void editDemand() {
        Mockito.doNothing().when(demandRepository).editDemand(Mockito.any());
        demandService.editDemand(demandInfoDto);
        Mockito.verify(demandRepository,Mockito.times(1)).editDemand(demandInfoDto);
    }


}
