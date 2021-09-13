package com.backend.passthemon.repository;

import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;

import java.util.List;

public interface DemandRepository {
    Demand addDemand(Demand demand);

    List<Demand> listAllDemandsByPage(Integer fetchPage, Integer category, Integer attrition);

    List<Demand> listOnesDemandsByPage(Integer fetchPage, User user);

    Demand getDemandById(Integer demandId);

    Integer editDemand(DemandInfoDto demandInfoDto,Integer userId);

    Integer removeDemand(Integer demandId,Integer userId);

    void changeState(Integer demandId, Integer state);

    Integer countDemandsByUserAndStateIsNot(User user, Integer state);
}
