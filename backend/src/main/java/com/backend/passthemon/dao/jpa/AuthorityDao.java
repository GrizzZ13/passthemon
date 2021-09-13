package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Authority;
import com.backend.passthemon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityDao extends JpaRepository<Authority,Integer> {
    List<Authority> findAllByUser(User user);
}
