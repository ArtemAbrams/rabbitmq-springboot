package com.example.productservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Timestamp timestamp;
    private boolean cardNonLocked;
    private double balance;
    @OneToOne(mappedBy = "cardAccount")
    private Card card;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Transaction> transactions;
    public void addTransaction(Transaction transaction){
        if(transactions==null){
            transactions = new ArrayList<>();
            transactions.add(transaction);
        }
        transactions.add(transaction);
    }
}
