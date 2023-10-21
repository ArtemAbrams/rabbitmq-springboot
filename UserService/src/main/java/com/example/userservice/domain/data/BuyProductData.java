package com.example.userservice.domain.data;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyProductData implements Serializable {
    private String username;
    private String cardNumber;
    private String productName;
}
