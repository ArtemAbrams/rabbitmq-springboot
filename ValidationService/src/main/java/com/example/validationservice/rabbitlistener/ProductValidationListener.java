package com.example.validationservice.rabbitlistener;

import com.example.validationservice.domain.data.BuyProductData;
import com.example.validationservice.domain.data.WriteOffMoneyData;
import com.example.validationservice.repository.ProductRepository;
import com.example.validationservice.validation.ProductValidation;
import com.example.validationservice.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidationListener {
    private final UserValidation userValidation;
    private final ProductValidation productValidation;
    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;
    @RabbitListener(queues = "validationBuyProduct")
    public void BuyProductValidation(BuyProductData buyProductData){
        if (
                userValidation.isUserCorrect(buyProductData.getUsername()) &&
                        productValidation.isProductExist(buyProductData.getProductName()) &&
                        userValidation.isCardNonLocked(buyProductData.getCardNumber()) &&
                        userValidation.isUserHasCard(buyProductData.getCardNumber(), buyProductData.getUsername()) &&
                        userValidation.isUserHasEnoughMoney(buyProductData.getCardNumber(), buyProductData.getProductName())
        ) {
          var price = productRepository.findProductByName(buyProductData.getProductName())
                          .get()
                          .getPrice();
          rabbitTemplate.convertAndSend("product","routingKey4", WriteOffMoneyData.builder()
                  .cardNumber(buyProductData.getCardNumber())
                          .price(price)
                  .build());
        }
    }
}
