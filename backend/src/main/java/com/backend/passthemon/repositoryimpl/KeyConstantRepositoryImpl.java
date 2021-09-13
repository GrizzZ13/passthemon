package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.KeyConstantDao;
import com.backend.passthemon.entity.KeyConstant;
import com.backend.passthemon.repository.KeyConstantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KeyConstantRepositoryImpl implements KeyConstantRepository {
    @Autowired
    KeyConstantDao keyConstantDao;

    @Override
    public String getValueByName(String name){
        KeyConstant keyConstant=keyConstantDao.getKeyConstantByName(name);
        return keyConstant.getValue();
    }
    @Override
    public void updateValueByName(String name,String value){
        KeyConstant keyConstant=keyConstantDao.getKeyConstantByName(name);
        if(keyConstant==null){
            KeyConstant newKeyConstant=new KeyConstant(name,value);
            keyConstantDao.saveAndFlush(newKeyConstant);
        }
        else {
            keyConstant.setValue(value);
            keyConstantDao.saveAndFlush(keyConstant);
        }
    }
    @Override
    public boolean checkStatusCode(String name,String value){
        KeyConstant keyConstant=keyConstantDao.getKeyConstantByName(name);
        if (keyConstant==null) return true;
        if (value==null || value.isEmpty()) return false;
        return keyConstant.getValue().equals(value);
    }
}
