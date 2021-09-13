package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandDao extends JpaRepository<Demand,Integer> {
    Demand findDemandById(Integer id);
    Page<Demand> findAllByUserAndStateIn(Pageable pageable, User user, List<Integer> stateList);
    Page<Demand> findAllByState(Integer state, Pageable pageable);
    Page<Demand> findAllByStateAndAttrition(Integer state, Integer attrition, Pageable pageable);
    Page<Demand> findAllByStateAndCategory(Integer state, Integer category, Pageable pageable);
    Page<Demand> findAllByStateAndAttritionAndCategory(Integer state, Integer attrition, Integer category, Pageable pageable);
    Integer countDemandsByUserAndStateIsNot(User user, Integer state);
}
