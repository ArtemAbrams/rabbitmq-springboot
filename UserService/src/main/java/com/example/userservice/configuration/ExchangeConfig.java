package com.example.userservice.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {
    @Bean(name = "emailSender_exchange")
    public DirectExchange emailSenderExchange() {
        return new DirectExchange("emailSenderExchange", true, false);
    }
    @Bean(name = "validation_exchange")
    public DirectExchange validationExchange() {
        return new DirectExchange("validation", true, false);
    }
    @Bean(name = "product_exchange")
    public DirectExchange productExchange() {
        return new DirectExchange("product", true, false);
    }
}
