package com.example.pcmarketsecurity.repository;


import com.example.pcmarketsecurity.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
