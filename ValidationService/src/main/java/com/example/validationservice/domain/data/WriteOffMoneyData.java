package com.example.validationservice.domain.data;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteOffMoneyData implements Serializable {
    private String cardNumber;
    private double price;
}
