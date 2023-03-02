package com.example.pcmarketsecurity.repository;


import com.example.pcmarketsecurity.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
