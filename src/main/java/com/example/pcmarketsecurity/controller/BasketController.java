package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.diler.BasketDto;
import com.example.pcmarketsecurity.entity.Basket;
import com.example.pcmarketsecurity.entity.Product;
import com.example.pcmarketsecurity.repository.BasketRepository;
import com.example.pcmarketsecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/basket")
public class BasketController {
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    ProductRepository productRepository;


    //    create
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addBasket(@RequestBody BasketDto basketDto) {

        Basket basket = new Basket();

        Optional<Product> optionalProduct = productRepository.findById(basketDto.getProduct());
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();

        basket.setProduct(optionalProduct.get());
        basket.setNumberProduct(basketDto.getNumberProduct());

        Basket saveBasket = basketRepository.save(basket);
        return ResponseEntity.ok(saveBasket);
    }

    //    Get
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getBasket() {
        return ResponseEntity.ok(basketRepository.findAll());
    }

    //    get by id
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getBasketById(@PathVariable Integer id) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        return ResponseEntity.status(optionalBasket.isPresent() ? 200 : 404).body(optionalBasket.orElse(null));
    }

    //    Update
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editBasket(@PathVariable Integer id, @RequestBody BasketDto basketDto) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        if (!optionalBasket.isPresent())
            return ResponseEntity.notFound().build();

        Basket editingBasket = optionalBasket.get();

        Optional<Product> optionalProduct = productRepository.findById(basketDto.getProduct());
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();

        editingBasket.setProduct(optionalProduct.get());
        editingBasket.setNumberProduct(basketDto.getNumberProduct());

        Basket saveBasket = basketRepository.save(editingBasket);
        return ResponseEntity.ok(saveBasket);
    }

    //    Delete
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBasket(@PathVariable Integer id) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        if (!optionalBasket.isPresent())
            return ResponseEntity.notFound().build();

        basketRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
