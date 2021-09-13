package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.KeyConstant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyConstantDao extends JpaRepository<KeyConstant,Integer> {
    KeyConstant getKeyConstantByName(String name);
}
