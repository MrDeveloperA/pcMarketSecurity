package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.diler.BasketDto;
import com.example.pcmarketsecurity.diler.ReviewDto;
import com.example.pcmarketsecurity.entity.Basket;
import com.example.pcmarketsecurity.entity.Product;
import com.example.pcmarketsecurity.entity.Review;
import com.example.pcmarketsecurity.entity.User;
import com.example.pcmarketsecurity.repository.BasketRepository;
import com.example.pcmarketsecurity.repository.ProductRepository;
import com.example.pcmarketsecurity.repository.ReviewRepository;
import com.example.pcmarketsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;


    //    create
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addReview(@RequestBody ReviewDto reviewDto) {

        Review review = new Review();
        review.setStar(reviewDto.getStar());

        Optional<User> optionalUser = userRepository.findById(reviewDto.getUser());
        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();
        review.setUser(optionalUser.get());
        review.setMessage(reviewDto.getMessage());

        Review saveReview = reviewRepository.save(review);
        return ResponseEntity.ok(saveReview);
    }

    //    Get
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getReview() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    //    get by id
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getReviewById(@PathVariable Integer id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        return ResponseEntity.status(optionalReview.isPresent() ? 200 : 404).body(optionalReview.orElse(null));
    }

    //    Update
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editReview(@PathVariable Integer id, @RequestBody ReviewDto reviewDto) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (!optionalReview.isPresent())
            return ResponseEntity.notFound().build();

        Review editingReview = optionalReview.get();
        editingReview.setStar(reviewDto.getStar());

        Optional<User> optionalUser = userRepository.findById(reviewDto.getUser());
        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();
        editingReview.setUser(optionalUser.get());
        editingReview.setMessage(reviewDto.getMessage());

        Review saveReview = reviewRepository.save(editingReview);
        return ResponseEntity.ok(saveReview);
    }

    //    Delete
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteReview(@PathVariable Integer id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (!optionalReview.isPresent())
            return ResponseEntity.notFound().build();

        reviewRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
