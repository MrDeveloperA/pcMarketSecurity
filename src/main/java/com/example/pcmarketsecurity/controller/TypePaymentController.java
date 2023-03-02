package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.entity.Brand;
import com.example.pcmarketsecurity.entity.TypePayment;
import com.example.pcmarketsecurity.repository.BrandRepository;
import com.example.pcmarketsecurity.repository.TypePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/typePayment")
public class TypePaymentController {
    @Autowired
    TypePaymentRepository typePaymentRepository;

    //    Create
//    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addTypePayment(@RequestBody TypePayment typePayment) {

        TypePayment typePayment1 = new TypePayment();
        typePayment1.setName(typePayment.getName());

        TypePayment saveTypePayment = typePaymentRepository.save(typePayment1);
        return ResponseEntity.ok(saveTypePayment);
    }

    //    Get
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getTypePayment() {
        return ResponseEntity.ok(typePaymentRepository.findAll());
    }

    //   get by id
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getTypePaymentById(@PathVariable Integer id) {
        Optional<TypePayment> optionalTypePayment = typePaymentRepository.findById(id);
        return ResponseEntity.status(optionalTypePayment.isPresent() ? 200 : 404).body(optionalTypePayment.orElse(null));
    }

    //    Update
    //    SUPER_ADMIN, MODERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editTypePayment(@PathVariable Integer id, @RequestBody TypePayment typePayment) {
        Optional<TypePayment> optionalTypePayment = typePaymentRepository.findById(id);
        if (!optionalTypePayment.isPresent())
            return ResponseEntity.notFound().build();

        TypePayment editingTypePayment = optionalTypePayment.get();
        editingTypePayment.setName(typePayment.getName());

        TypePayment save = typePaymentRepository.save(editingTypePayment);
        return ResponseEntity.ok(save);
    }

    //    Delete
    //    SUPER_ADMIN
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteTypePayment(@PathVariable Integer id) {
        Optional<TypePayment> optionalTypePayment = typePaymentRepository.findById(id);
        if (!optionalTypePayment.isPresent())
            return ResponseEntity.notFound().build();

        typePaymentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
