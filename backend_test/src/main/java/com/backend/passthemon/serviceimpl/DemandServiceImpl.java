package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.dto.DemandInfoDto;
import com.backend.passthemon.entity.Demand;
import com.backend.passthemon.entity.User;
import com.backend.passthemon.repository.DemandRepository;
import com.backend.passthemon.repository.UserRepository;
import com.backend.passthemon.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandServiceImpl implements DemandService {
    @Autowired
    DemandRepository demandRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Integer addDemand(Demand demand){
        return demandRepository.addDemand(demand);
    }

    @Override
    public List<Demand> listAllDemandsByPage(Integer fetchPage, Integer category, Integer attrition){
        return demandRepository.listAllDemandsByPage(fetchPage, category, attrition);
    }
    @Override
    public List<Demand> listOnesDemandsByPage(Integer fetchPage, Integer userId){
        User user = new User(userId);

        return demandRepository.listOnesDemandsByPage(fetchPage, user);
    }
    @Override
    public  void removeDemand(Integer demandId){
        demandRepository.removeDemand(demandId);
    }
    @Override
    public  Demand getDemandById(Integer demandId){
        return demandRepository.getDemandById(demandId);
    }
    @Override
    public void editDemand(DemandInfoDto demandInfoDto){
        demandRepository.editDemand(demandInfoDto);
    }
    @Override
    public void changeState(Integer demandId, Integer state){
        demandRepository.changeState(demandId,state);
    }
}
