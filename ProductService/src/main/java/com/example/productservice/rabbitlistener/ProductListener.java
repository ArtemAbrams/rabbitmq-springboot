package com.example.productservice.rabbitlistener;

import com.example.productservice.domain.data.WriteOffMoneyData;
import com.example.productservice.domain.entity.Transaction;
import com.example.productservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProductListener {
    private final CardRepository cardRepository;
    @RabbitListener(queues = "productBuy")
    @Transactional
    public void BuyProduct(WriteOffMoneyData changeMoneyData){
       var account = cardRepository.findCardByNumber(changeMoneyData.getCardNumber())
               .get()
               .getCardAccount();
       var balance = account.getBalance();
       account.setBalance(balance - changeMoneyData.getPrice());
       var transaction = Transaction.builder()
               .transaction("-"+changeMoneyData.getPrice())
               .build();
       account.addTransaction(transaction);
    }
}
