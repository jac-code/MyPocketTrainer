package com.example.demo.repository;

import com.example.demo.model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(final String token);
    Long removeByToken(String token);
}