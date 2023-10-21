package com.example.productservice.repository;

import com.example.productservice.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findCardByNumber(String number);
}
