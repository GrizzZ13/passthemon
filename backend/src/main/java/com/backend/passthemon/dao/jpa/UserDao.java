package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
    User findUserById(Integer userId);
    User getUserByEmail(String email);
}
