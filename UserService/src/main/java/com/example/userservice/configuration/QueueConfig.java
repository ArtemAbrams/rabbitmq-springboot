package com.example.userservice.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    @Bean(name = "mail_queue")
    public Queue myQueue() {
        return new Queue("mail");
    }
    @Bean(name = "mail_queue_login")
    public Queue loginQueue() {
        return new Queue("mailLogin");
    }
    @Bean(name = "validation_buy_product")
    public Queue validationBuyProduct() {
        return new Queue("validationBuyProduct");
    }
    @Bean(name = "product_buy")
    public Queue BuyProduct() {
        return new Queue("productBuy");
    }
}
