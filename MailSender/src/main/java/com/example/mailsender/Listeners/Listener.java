package com.example.mailsender.Listeners;

import com.example.mailsender.repository.UserRepository;
import com.example.mailsender.service.impl.EmailSenderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class Listener {
    private final UserRepository userRepository;
    private final EmailSenderImpl emailSender;
    @RabbitListener(queues = "mail")
    public void sendMail(String username){
        var user = userRepository.findUserByEmail(username);
        var uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/user/registrationConfirm")
                .queryParam("username", username)
                .build()
                .toUriString();
        user.ifPresent(value -> emailSender.SendEmail(value
                        .getEmail(),
                "Confirm your registration",
                uri));
    }
    @RabbitListener(queues = "mailLogin")
    public void loginSendMail(String username){
        var user = userRepository.findUserByEmail(username);
        var uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/user/locked")
                .queryParam("username", username)
                .build()
                .toUriString();
        user.ifPresent(value -> emailSender.SendEmail(value
                        .getEmail(),
                "Log in" +
                        "someone has logged into your account, if it's not you, you can lock your account",
                uri));
    }
}
