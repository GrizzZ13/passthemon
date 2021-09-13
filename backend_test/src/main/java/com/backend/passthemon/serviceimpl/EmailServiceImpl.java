package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendSimpleEmail(String from, String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // sender
        simpleMailMessage.setFrom(from);
        // receiver
        simpleMailMessage.setTo(to);
        // subject
        simpleMailMessage.setSubject(subject);
        // content
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
}
