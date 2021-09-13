package com.backend.passthemon.dao.jpa;

import com.backend.passthemon.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, Integer> {
    List<Notification> getAllByTypeAndExpiredGreaterThanEqual(Integer type, Date expired);
}
