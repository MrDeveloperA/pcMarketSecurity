package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.entity.Brand;
import com.example.pcmarketsecurity.entity.Category;
import com.example.pcmarketsecurity.repository.BrandRepository;
import com.example.pcmarketsecurity.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    //    Create
//    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> addCategory(@RequestBody Category category) {

        Category category1 = new Category();
        category1.setName(category.getName());

        Category saveCategory = categoryRepository.save(category1);
        return ResponseEntity.ok(saveCategory);
    }

    //    Get
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public HttpEntity<?> getCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    //   get by id
    //    SUPER_ADMIN, MODERATOR, OPERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getCategoryById(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return ResponseEntity.status(optionalCategory.isPresent() ? 200 : 404).body(optionalCategory.orElse(null));
    }

    //    Update
    //    SUPER_ADMIN, MODERATOR
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id, @RequestBody Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return ResponseEntity.notFound().build();

        Category editingCategory = optionalCategory.get();
        editingCategory.setName(category.getName());

        Category saveCategory = categoryRepository.save(editingCategory);
        return ResponseEntity.ok(saveCategory);
    }

    //    Delete
    //    SUPER_ADMIN
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategory(@PathVariable Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return ResponseEntity.notFound().build();

        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
