package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Verification;

public interface VerificationRepository {
    Verification save(Verification verification);
    Verification getVerificationByEmail(String email);
}
