package com.example.mailsender.service.impl;

import com.example.mailsender.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    private final JavaMailSender javaMailSender;
    @Value(value = "${spring.mail.username}")
    private String fromUsername;
    @Override
    public void SendEmail(String toEmail, String subject, String body) {
      var message = new SimpleMailMessage();
      message.setFrom(fromUsername);
      message.setTo(toEmail);
      message.setSubject(subject);
      message.setText(body);
      javaMailSender.send(message);
    }
}
