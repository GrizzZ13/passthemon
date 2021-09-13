package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Verification;
import com.backend.passthemon.repository.VerificationRepository;
import com.backend.passthemon.service.VerificationService;
import com.backend.passthemon.utils.emailutils.EmailUtil;
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

    @Override
    @Transactional
    public Verification generateAndSave(String email) {
        Verification verification = verificationRepository.getVerificationByEmail(email);
        if(verification==null){
            verification = verificationRepository.save(new Verification(email));
        }
        String verificationCode = EmailUtil.generateVerificationCode(EmailUtil.FIXED_LENGTH);
        Long expiredMillis = System.currentTimeMillis() + 1000L * 60 * 15;
        verification.setCode(verificationCode);
        verification.setTime(expiredMillis);
        verification.setEmail(email);
        return verificationRepository.save(verification);
    }
}
