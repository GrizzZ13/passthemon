package com.backend.passthemon.repository;

import com.backend.passthemon.entity.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    List<Notification> getAllOrdinaryByExpiredGreaterThanEqual(Date date);
    List<Notification> getAllDialogByExpiredGreaterThanEqual(Date date);
}
