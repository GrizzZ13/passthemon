package com.backend.passthemon.repositoryimpl;

import com.backend.passthemon.constant.NotificationConstant;
import com.backend.passthemon.dao.jpa.NotificationDao;
import com.backend.passthemon.entity.Notification;
import com.backend.passthemon.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    @Autowired
    NotificationDao notificationDao;

    @Override
    public Notification save(Notification notification) {
        return notificationDao.saveAndFlush(notification);
    }

    @Override
    public List<Notification> getAllOrdinaryByExpiredGreaterThanEqual(Date date) {
        return notificationDao.getAllByTypeAndExpiredGreaterThanEqual(NotificationConstant.ORDINARY_TYPE, date);
    }

    @Override
    public List<Notification> getAllDialogByExpiredGreaterThanEqual(Date date) {
        return notificationDao.getAllByTypeAndExpiredGreaterThanEqual(NotificationConstant.DIALOG_TYPE, date);
    }
}
