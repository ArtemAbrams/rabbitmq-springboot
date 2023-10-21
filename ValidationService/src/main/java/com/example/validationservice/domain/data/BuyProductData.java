package com.example.validationservice.domain.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyProductData implements Serializable {
    private String username;
    private String cardNumber;
    private String productName;
}
