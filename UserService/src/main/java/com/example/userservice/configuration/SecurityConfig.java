package com.example.userservice.configuration;

import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        var user = new DaoAuthenticationProvider();
        user.setPasswordEncoder(passwordEncoder());
        user.setUserDetailsService(userDetailsService());
        return user;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(() -> new RuntimeException("User with email " + username + " was not found"));
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
