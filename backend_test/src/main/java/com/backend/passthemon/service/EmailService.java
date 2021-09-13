package com.backend.passthemon.service;

import org.springframework.stereotype.Service;

public interface EmailService {
    void sendSimpleEmail(String from, String to, String subject, String text);
}
