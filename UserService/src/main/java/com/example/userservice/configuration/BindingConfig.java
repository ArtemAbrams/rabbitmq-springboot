package com.example.userservice.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {
    @Autowired
    @Qualifier(value = "mail_queue")
    private Queue emailQueue;
    @Autowired
    @Qualifier(value = "mail_queue_login")
    private Queue loginQueue;
    @Autowired
    @Qualifier(value = "emailSender_exchange")
    private DirectExchange emailExchange;
    @Autowired
    @Qualifier(value = "validation_exchange")
    private DirectExchange validationExchange;
    @Autowired
    @Qualifier(value = "validation_buy_product")
    private Queue productBuyQueue;

    @Autowired
    @Qualifier(value = "product_exchange")
    private DirectExchange productExchange;
    @Autowired
    @Qualifier(value = "product_buy")
    private Queue buyProductQueue;

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue)
                .to(emailExchange)
                .with("routingKey1");
    }
    @Bean
    public Binding emailLoginBinding() {
        return BindingBuilder.bind(loginQueue)
                .to(emailExchange)
                .with("routingKey2");
    }
    @Bean
    public Binding validationBuyProductBinding() {
        return BindingBuilder.bind(productBuyQueue)
                .to(validationExchange)
                .with("routingKey3");
    }
    @Bean
    public Binding buyProductBinding() {
        return BindingBuilder.bind(buyProductQueue)
                .to(productExchange)
                .with("routingKey4");
    }
}
