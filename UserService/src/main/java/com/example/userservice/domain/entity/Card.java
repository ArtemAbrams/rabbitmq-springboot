package com.example.userservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private int cvv;
    @Pattern(regexp = "^[0-9]{2}/[0-9]{2}$", message = "2 number/2 number")
    private String dateExpiration;
    @ManyToOne
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private CardAccount cardAccount;
}
