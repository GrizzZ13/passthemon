package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Notification;
import com.backend.passthemon.exception.MyException;
import com.backend.passthemon.repository.NotificationRepository;
import com.backend.passthemon.service.KeyConstantService;
import com.backend.passthemon.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.backend.passthemon.constant.NotificationConstant.ORDINARY_TYPE;
import static com.backend.passthemon.constant.NotificationConstant.STATUS_CODE;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    KeyConstantService keyConstantService;

    @Override
    @Transactional
    public void publishNotification(String title, String content, Integer type, Date date, Date expired) throws MyException {
        if(title==null || title.isEmpty() || content==null || content.isEmpty() || type==null || date==null || expired==null){
            throw new MyException("incomplete notification details");
        }
        Notification notification;
        notification = new Notification(title, content, type, date, expired);
        notificationRepository.save(notification);
        if(type.equals(ORDINARY_TYPE)){
            String value= UUID.randomUUID().toString();
            keyConstantService.updateValueByName(STATUS_CODE,value);
        }
    }

    @Override
    public List<Notification> getAllOrdinary() {
        return notificationRepository.getAllOrdinaryByExpiredGreaterThanEqual(new Date());
    }

    @Override
    public List<Notification> getAllDialog() {
        return notificationRepository.getAllDialogByExpiredGreaterThanEqual(new Date());
    }
}
