package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.KeyConstant;
import com.backend.passthemon.repository.KeyConstantRepository;
import com.backend.passthemon.service.KeyConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyConstantServiceImpl implements KeyConstantService {
    @Autowired
    KeyConstantRepository keyConstantRepository;
    @Override
    public String getValueByName(String name){
        return keyConstantRepository.getValueByName(name);
    }
    @Override
    public void updateValueByName(String name,String value){
        keyConstantRepository.updateValueByName(name,value);
    }
    @Override
    public  boolean checkStatusCode(String name,String value){
        return keyConstantRepository.checkStatusCode(name,value);
    }
}
