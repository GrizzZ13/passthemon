package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationDao extends JpaRepository<Verification, Integer> {
    Verification getVerificationByEmail(String email);
}
