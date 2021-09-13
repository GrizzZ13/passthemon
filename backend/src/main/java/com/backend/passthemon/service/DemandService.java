package com.backend.passthemon.service;

import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;

import java.util.List;

public interface DemandService {
    Demand addDemand(Demand demand);
    List<Demand> listAllDemandsByPage(Integer fetchPage, Integer category, Integer attrition);
    List<Demand> listOnesDemandsByPage(Integer fetchPage, Integer userId);
    Demand getDemandById(Integer demandId);
    Integer editDemand(DemandInfoDto demandInfoDto,Integer userId);
    Integer removeDemand(Integer demandId,Integer userId);
    void changeState(Integer demandId, Integer state);
}
