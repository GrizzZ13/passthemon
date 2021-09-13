package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.AuthorityDao;
import com.backend.passthemon.entity.Authority;
import com.backend.passthemon.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorityRepositoryImpl implements AuthorityRepository {
    @Autowired
    AuthorityDao authorityDao;
    public void addAuthority(List<Authority> authorities){
        for(Authority authority:authorities){
            authorityDao.saveAndFlush(authority);
        }
    }
}
