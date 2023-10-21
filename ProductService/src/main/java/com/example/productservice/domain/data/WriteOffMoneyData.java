package com.example.productservice.domain.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriteOffMoneyData implements Serializable {
    private String cardNumber;
    private double price;
}
