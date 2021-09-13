package com.backend.passthemon.service;
import com.backend.passthemon.entity.Notification;
import com.backend.passthemon.exception.MyException;

import java.util.Date;
import java.util.List;

public interface NotificationService {
    void publishNotification(String title,
                             String content,
                             Integer type,
                             Date date,
                             Date expired) throws MyException;

    List<Notification> getAllOrdinary();
    List<Notification> getAllDialog();
}
