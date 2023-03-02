package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.diler.BasketDto;
import com.example.pcmarketsecurity.diler.ProductDto;
import com.example.pcmarketsecurity.entity.*;
import com.example.pcmarketsecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CategoryRepository categoryRepository;


    //    create
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto) {

        Product product = new Product();

        product.setName(productDto.getName());

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachment());
        if (!optionalAttachment.isPresent())
            return ResponseEntity.notFound().build();
        product.setAttachment(optionalAttachment.get());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        Optional<Brand> optionalBrand = brandRepository.findById(productDto.getBrand());
        if (!optionalBrand.isPresent())
            return ResponseEntity.notFound().build();
        product.setBrand(optionalBrand.get());

        Optional<Review> optionalReview = reviewRepository.findById(productDto.getReview());
        if (!optionalReview.isPresent())
            return ResponseEntity.notFound().build();
        product.setReview(optionalReview.get());

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategory());
        if (!optionalCategory.isPresent())
            return ResponseEntity.notFound().build();
        product.setCategory(optionalCategory.get());

        Product saveProduct = productRepository.save(product);
        return ResponseEntity.ok(saveProduct);
    }

    //    Get
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getProduct() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    //    get by id
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity.status(optionalProduct.isPresent() ? 200 : 404).body(optionalProduct.orElse(null));
    }

    //    Update
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();

        Product editingProduct = optionalProduct.get();

        editingProduct.setName(productDto.getName());

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachment());
        if (!optionalAttachment.isPresent())
            return ResponseEntity.notFound().build();
        editingProduct.setAttachment(optionalAttachment.get());
        editingProduct.setDescription(productDto.getDescription());
        editingProduct.setPrice(productDto.getPrice());

        Optional<Brand> optionalBrand = brandRepository.findById(productDto.getBrand());
        if (!optionalBrand.isPresent())
            return ResponseEntity.notFound().build();
        editingProduct.setBrand(optionalBrand.get());

        Optional<Review> optionalReview = reviewRepository.findById(productDto.getReview());
        if (!optionalReview.isPresent())
            return ResponseEntity.notFound().build();
        editingProduct.setReview(optionalReview.get());

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategory());
        if (!optionalCategory.isPresent())
            return ResponseEntity.notFound().build();
        editingProduct.setCategory(optionalCategory.get());

        Product saveProduct = productRepository.save(editingProduct);
        return ResponseEntity.ok(saveProduct);
    }

    //    Delete
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();

        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
