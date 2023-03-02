package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.diler.BasketDto;
import com.example.pcmarketsecurity.entity.Basket;
import com.example.pcmarketsecurity.entity.Brand;
import com.example.pcmarketsecurity.entity.Product;
import com.example.pcmarketsecurity.repository.BasketRepository;
import com.example.pcmarketsecurity.repository.BrandRepository;
import com.example.pcmarketsecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    BrandRepository brandRepository;

    //    Create
//    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addBrand(@RequestBody Brand brand) {

        Brand brand1 = new Brand();
        brand1.setName(brand.getName());

        Brand saveBrand = brandRepository.save(brand1);
        return ResponseEntity.ok(saveBrand);
    }

    //    Get
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getBrand() {
        return ResponseEntity.ok(brandRepository.findAll());
    }

    //   get by id
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getBrandById(@PathVariable Integer id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        return ResponseEntity.status(optionalBrand.isPresent() ? 200 : 404).body(optionalBrand.orElse(null));
    }

    //    Update
    //    SUPER_ADMIN, MODERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editBrand(@PathVariable Integer id, @RequestBody Brand brand) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (!optionalBrand.isPresent())
            return ResponseEntity.notFound().build();

        Brand editingBrand = optionalBrand.get();
        editingBrand.setName(brand.getName());

        Brand saveBrand = brandRepository.save(editingBrand);
        return ResponseEntity.ok(saveBrand);
    }

    //    Delete
    //    SUPER_ADMIN
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBrand(@PathVariable Integer id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (!optionalBrand.isPresent())
            return ResponseEntity.notFound().build();

        brandRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
