package com.example.userservice.controller;


import com.example.userservice.domain.data.BuyProductData;
import com.example.userservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final RabbitTemplate rabbitTemplate;
    @PostMapping("/buyProduct")
    public ResponseEntity<?> buyProduct(@RequestParam("cardNumber") String cardNumber,
                                        @RequestParam("productName") String productName){
        var user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        var buyProduct = BuyProductData.builder()
                .username(user.getUsername())
                .productName(productName)
                .cardNumber(cardNumber)
                .build();
        rabbitTemplate.convertAndSend("validation","routingKey3",buyProduct);
        return ResponseEntity.ok("Send message to another service");
    }
}
