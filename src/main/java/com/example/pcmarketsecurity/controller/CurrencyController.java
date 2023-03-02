package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.entity.Brand;
import com.example.pcmarketsecurity.entity.Category;
import com.example.pcmarketsecurity.entity.Currency;
import com.example.pcmarketsecurity.repository.BrandRepository;
import com.example.pcmarketsecurity.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    @Autowired
    CurrencyRepository currencyRepository;

    //    Create
//    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addCurrency(@RequestBody Currency currency) {

        Currency currency1 = new Currency();
        currency1.setName(currency.getName());

        Currency saveCurrency = currencyRepository.save(currency1);
        return ResponseEntity.ok(saveCurrency);
    }

    //    Get
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getCurrency() {
        return ResponseEntity.ok(currencyRepository.findAll());
    }

    //   get by id
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCurrencyById(@PathVariable Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        return ResponseEntity.status(optionalCurrency.isPresent() ? 200 : 404).body(optionalCurrency.orElse(null));
    }

    //    Update
    //    SUPER_ADMIN, MODERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCurrency(@PathVariable Integer id, @RequestBody Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent())
            return ResponseEntity.notFound().build();

        Currency editingCurrency = optionalCurrency.get();
        editingCurrency.setName(currency.getName());

        Currency saveCurrency = currencyRepository.save(editingCurrency);
        return ResponseEntity.ok(saveCurrency);
    }

    //    Delete
    //    SUPER_ADMIN
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCurrency(@PathVariable Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent())
            return ResponseEntity.notFound().build();

        currencyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
