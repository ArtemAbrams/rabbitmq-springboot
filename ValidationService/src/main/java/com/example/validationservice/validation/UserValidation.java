package com.example.validationservice.validation;

import com.example.validationservice.domain.entity.Card;
import com.example.validationservice.domain.entity.User;
import com.example.validationservice.repository.CardRepository;
import com.example.validationservice.repository.ProductRepository;
import com.example.validationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final ProductRepository productRepository;
    public boolean isUserCorrect(String username){
        return userRepository.findUserByEmail(username)
                .isPresent();
    }
    @Transactional
    public boolean isUserHasCard(String number, String username){
        return userRepository.findUserByEmail(username)
                .get()
                .getCards()
                .stream()
                .map(Card::getNumber)
                .anyMatch(e->e.equals(number));
    }
    public boolean isCardNonLocked(String cardNumber){
        return cardRepository.findCardByNumber(cardNumber)
                .get()
                .getCardAccount()
                .isCardNonLocked();
    }
    @Transactional
    public boolean isUserHasEnoughMoney(String cardNumber, String name){
        var price =  productRepository.findProductByName(name)
                .get()
                .getPrice();
       var moneyOnCard = cardRepository.findCardByNumber(cardNumber)
               .get()
               .getCardAccount()
               .getBalance();
       return moneyOnCard >= price;
    }
}
