package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NotificationDto {

    private String title;

    private String content;

    private String date;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private NotificationDto(){}

    public static NotificationDto convert(Notification notification){
        Date date = notification.getDate();
        String dateStr = sdf.format(date);
        return NotificationDto.builder()
                .title(notification.getTitle())
                .content(notification.getContent())
                .date(dateStr)
                .build();
    }

    public static List<NotificationDto> convert(List<Notification> notificationList){
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for (Notification notification : notificationList){
            notificationDtoList.add(convert(notification));
        }
        return notificationDtoList;
    }
}
