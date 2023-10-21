package com.example.userservice.controller;

import com.example.userservice.domain.data.UserDate;
import com.example.userservice.domain.data.UserLogin;
import com.example.userservice.domain.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.service.impl.JwtServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceIml jwtServiceIml;
   @PostMapping("/registration")
   public ResponseEntity<?> register(@RequestBody UserDate userDate){
       var user = User.builder()
               .email(userDate.getEmail())
               .accountNonExpired(true)
               .accountNonLocked(true)
               .credentialsNonExpired(true)
               .name(userDate.getFirstname())
               .lastname(userDate.getLastname())
               .enabled(false)
               .password(passwordEncoder.encode(userDate.getPassword()))
               .build();
       userRepository.saveAndFlush(user);
       rabbitTemplate.convertAndSend("emailSenderExchange",
               "routingKey1",
               userDate.getEmail());
       return ResponseEntity.status(HttpStatus.OK)
               .body("you are registered");
   }
    @GetMapping("/registrationConfirm")
    @Transactional
    public ResponseEntity<?> emailConfirm(@RequestParam("username") String username){

        userRepository.findUserByEmail(username)
                        .ifPresent(e->e.setEnabled(true));
        return ResponseEntity.status(HttpStatus.OK)
                .body("You have confirmed your email");
    }
    @GetMapping("/locked")
    @Transactional
    public ResponseEntity<?> lockAccount(@RequestParam("username") String username){

        userRepository.findUserByEmail(username)
                .ifPresent(e->e.setAccountNonLocked(false));
        return ResponseEntity.status(HttpStatus.OK)
                .body("You have locked your email");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userDate){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          userDate.getEmail(),
          userDate.getPassword()
       ));
       var user = userRepository.findUserByEmail(userDate.getEmail())
                       .orElseThrow(() -> new RuntimeException("User was not found"));
        rabbitTemplate.convertAndSend("emailSenderExchange","routingKey2",userDate.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                      jwtServiceIml.generateToken(user)
                );
    }
}
