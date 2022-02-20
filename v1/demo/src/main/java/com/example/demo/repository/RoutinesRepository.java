package com.example.demo.repository;

import com.example.demo.model.Routine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutinesRepository extends JpaRepository<Routine, Long>{
    
}
