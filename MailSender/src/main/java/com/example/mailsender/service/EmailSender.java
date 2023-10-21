package com.example.mailsender.service;

public interface EmailSender {
    void SendEmail(String toEmail,
                   String subject,
                   String body);
}
