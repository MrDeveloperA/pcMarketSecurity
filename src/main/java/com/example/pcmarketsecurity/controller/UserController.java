package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.diler.BasketDto;
import com.example.pcmarketsecurity.diler.UserDto;
import com.example.pcmarketsecurity.entity.*;
import com.example.pcmarketsecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    TypePaymentRepository typePaymentRepository;


    //    create
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addUser(@RequestBody UserDto userDto) {

        User user = new User();
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());

        Optional<Basket> optionalBasket = basketRepository.findById(userDto.getBasket());
        if (!optionalBasket.isPresent())
            return ResponseEntity.notFound().build();
        user.setBasket(optionalBasket.get());

        Optional<Currency> optionalCurrency = currencyRepository.findById(userDto.getCurrency());
        if (!optionalCurrency.isPresent())
            return ResponseEntity.notFound().build();
        user.setCurrency(optionalCurrency.get());

        Optional<TypePayment> optionalTypePayment = typePaymentRepository.findById(userDto.getTypePayment());
        if (!optionalTypePayment.isPresent())
            return ResponseEntity.notFound().build();
        user.setTypePayment(optionalTypePayment.get());

        User saveUser = userRepository.save(user);
        return ResponseEntity.ok(saveUser);
    }

    //    Get
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    //    get by id
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return ResponseEntity.status(optionalUser.isPresent() ? 200 : 404).body(optionalUser.orElse(null));
    }

    //    Update
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();

        User editingUser = optionalUser.get();

        editingUser.setName(userDto.getName());
        editingUser.setAddress(userDto.getAddress());
        editingUser.setPhoneNumber(userDto.getPhoneNumber());
        editingUser.setEmail(userDto.getEmail());

        Optional<Basket> optionalBasket = basketRepository.findById(userDto.getBasket());
        if (!optionalBasket.isPresent())
            return ResponseEntity.notFound().build();
        editingUser.setBasket(optionalBasket.get());

        Optional<Currency> optionalCurrency = currencyRepository.findById(userDto.getCurrency());
        if (!optionalCurrency.isPresent())
            return ResponseEntity.notFound().build();
        editingUser.setCurrency(optionalCurrency.get());

        Optional<TypePayment> optionalTypePayment = typePaymentRepository.findById(userDto.getTypePayment());
        if (!optionalTypePayment.isPresent())
            return ResponseEntity.notFound().build();
        editingUser.setTypePayment(optionalTypePayment.get());

        User saveUser = userRepository.save(editingUser);
        return ResponseEntity.ok(saveUser);

    }

    //    Delete
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
