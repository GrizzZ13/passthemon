package com.backend.passthemon.service;

import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;

import java.util.List;

public interface DemandService {
    Integer addDemand(Demand demand);
    List<Demand> listAllDemandsByPage(Integer fetchPage, Integer category, Integer attrition);
    List<Demand> listOnesDemandsByPage(Integer fetchPage, Integer userId);
    Demand getDemandById(Integer demandId);
    void editDemand(DemandInfoDto demandInfoDto);
    void removeDemand(Integer demandId);
    void changeState(Integer demandId, Integer state);
}
