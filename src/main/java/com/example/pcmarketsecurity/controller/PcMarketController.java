package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.diler.BasketDto;
import com.example.pcmarketsecurity.diler.PcMarketDto;
import com.example.pcmarketsecurity.entity.*;
import com.example.pcmarketsecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pcMarket")
public class PcMarketController {
    @Autowired
    PcMarketRepository pcMarketRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    BrandRepository brandRepository;


    //    create
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addPcMarket(@RequestBody PcMarketDto pcMarketDto) {

        PcMarket pcMarket = new PcMarket();
        pcMarket.setName(pcMarketDto.getName());

        Optional<Product> optionalProduct = productRepository.findById(pcMarketDto.getProduct());
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();
        pcMarket.setProduct(optionalProduct.get());

        Optional<Review> optionalReview = reviewRepository.findById(pcMarketDto.getReview());
        if (!optionalReview.isPresent())
            return ResponseEntity.notFound().build();
        pcMarket.setReview(optionalReview.get());

        Optional<Partner> optionalPartner = partnerRepository.findById(pcMarketDto.getReview());
        if (!optionalPartner.isPresent())
            return ResponseEntity.notFound().build();
        pcMarket.setPartner(optionalPartner.get());

        Optional<Brand> optionalBrand = brandRepository.findById(pcMarketDto.getBrand());
        if (!optionalBrand.isPresent())
            return ResponseEntity.notFound().build();
        pcMarket.setBrand(optionalBrand.get());

        PcMarket savePcMarket = pcMarketRepository.save(pcMarket);
        return ResponseEntity.ok(savePcMarket);
    }

    //    Get
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getPcMarket() {
        return ResponseEntity.ok(pcMarketRepository.findAll());
    }

    //    get by id
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getPcMarketById(@PathVariable Integer id) {
        Optional<PcMarket> optionalPcMarket = pcMarketRepository.findById(id);
        return ResponseEntity.status(optionalPcMarket.isPresent() ? 200 : 404).body(optionalPcMarket.orElse(null));
    }

    //    Update
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editPcMarket(@PathVariable Integer id, @RequestBody PcMarketDto pcMarketDto) {
        Optional<PcMarket> optionalPcMarket = pcMarketRepository.findById(id);
        if (!optionalPcMarket.isPresent())
            return ResponseEntity.notFound().build();

        PcMarket editingPcMarket = optionalPcMarket.get();

        editingPcMarket.setName(pcMarketDto.getName());

        Optional<Product> optionalProduct = productRepository.findById(pcMarketDto.getProduct());
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();
        editingPcMarket.setProduct(optionalProduct.get());

        Optional<Review> optionalReview = reviewRepository.findById(pcMarketDto.getReview());
        if (!optionalReview.isPresent())
            return ResponseEntity.notFound().build();
        editingPcMarket.setReview(optionalReview.get());

        Optional<Partner> optionalPartner = partnerRepository.findById(pcMarketDto.getReview());
        if (!optionalPartner.isPresent())
            return ResponseEntity.notFound().build();
        editingPcMarket.setPartner(optionalPartner.get());

        Optional<Brand> optionalBrand = brandRepository.findById(pcMarketDto.getBrand());
        if (!optionalBrand.isPresent())
            return ResponseEntity.notFound().build();
        editingPcMarket.setBrand(optionalBrand.get());

        PcMarket savePcMarket = pcMarketRepository.save(editingPcMarket);
        return ResponseEntity.ok(savePcMarket);

    }

    //    Delete
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePcMarket(@PathVariable Integer id) {
        Optional<PcMarket> optionalPcMarket = pcMarketRepository.findById(id);
        if (!optionalPcMarket.isPresent())
            return ResponseEntity.notFound().build();

        pcMarketRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
