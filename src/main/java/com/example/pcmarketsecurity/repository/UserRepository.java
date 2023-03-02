package com.example.pcmarketsecurity.repository;

import com.example.pcmarketsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
