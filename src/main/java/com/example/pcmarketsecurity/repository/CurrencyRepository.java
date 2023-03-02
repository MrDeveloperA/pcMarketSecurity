package com.example.pcmarketsecurity.repository;

import com.example.pcmarketsecurity.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
