package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.dao.jpa.VerificationDao;
import com.backend.passthemon.entity.Verification;
import com.backend.passthemon.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationRepositoryImpl implements VerificationRepository {
    @Autowired
    VerificationDao verificationDao;

    @Override
    public Verification save(Verification verification) {
        return verificationDao.saveAndFlush(verification);
    }

    @Override
    public Verification getVerificationByEmail(String email) {
        return verificationDao.getVerificationByEmail(email);
    }
}
