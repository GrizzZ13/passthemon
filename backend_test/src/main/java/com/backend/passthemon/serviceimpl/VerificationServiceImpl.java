package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Verification;
import com.backend.passthemon.repository.VerificationRepository;
import com.backend.passthemon.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    VerificationRepository verificationRepository;

    @Transactional
    @Override
    public Verification save(Verification verification) {
        return verificationRepository.save(verification);
    }

    @Override
    public Verification getVerificationByEmail(String email) {
        return verificationRepository.getVerificationByEmail(email);
    }
}
