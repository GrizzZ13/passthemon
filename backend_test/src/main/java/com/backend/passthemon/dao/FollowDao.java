package com.backend.passthemon.dao;

import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowDao extends JpaRepository<Follow, Integer> {
    List<Follow> findAllByUser(User user);
    Page<Follow> findAllByUser(User user, Pageable pageable);
}
