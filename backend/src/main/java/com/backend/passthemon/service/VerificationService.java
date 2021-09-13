package com.backend.passthemon.service;

import com.backend.passthemon.entity.Verification;

public interface VerificationService {
    Verification save(Verification verification);
    Verification getVerificationByEmail(String email);
    Verification generateAndSave(String email);
}
