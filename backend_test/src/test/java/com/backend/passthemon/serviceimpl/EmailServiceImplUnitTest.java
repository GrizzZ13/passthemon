package com.backend.passthemon.serviceimpl;

import com.backend.passthemon.entity.Follow;
import com.backend.passthemon.repository.FollowRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImplUnitTest {
    @Mock
    JavaMailSender javaMailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    private SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    private String from = "from", to = "to", subject = "subject", text = "text";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void sendSimpleEmail() {
        emailService.sendSimpleEmail(from, to, subject, text);
        // sender
        simpleMailMessage.setFrom(from);
        // receiver
        simpleMailMessage.setTo(to);
        // subject
        simpleMailMessage.setSubject(subject);
        // content
        simpleMailMessage.setText(text);
        Mockito.verify(javaMailSender).send(simpleMailMessage);
    }
}
