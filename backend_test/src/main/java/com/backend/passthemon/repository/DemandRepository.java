package com.backend.passthemon.repository;

import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;

import java.util.List;

public interface DemandRepository {
    Integer addDemand(Demand demand);

    List<Demand> listAllDemandsByPage(Integer fetchPage, Integer category, Integer attrition);

    List<Demand> listOnesDemandsByPage(Integer fetchPage, User user);

    Demand getDemandById(Integer demandId);

    void editDemand(DemandInfoDto demandInfoDto);

    void removeDemand(Integer demandId);

    void changeState(Integer demandId, Integer state);
}
