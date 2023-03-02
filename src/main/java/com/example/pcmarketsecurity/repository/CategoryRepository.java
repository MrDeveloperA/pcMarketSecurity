package com.example.pcmarketsecurity.repository;

import com.example.pcmarketsecurity.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
