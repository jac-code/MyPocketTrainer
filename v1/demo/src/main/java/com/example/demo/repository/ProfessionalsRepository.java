package com.example.demo.repository;

import com.example.demo.model.Professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalsRepository extends JpaRepository<Professional, Long>{
    
}