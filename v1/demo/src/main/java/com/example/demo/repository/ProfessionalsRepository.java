package com.example.demo.repository;

import com.example.demo.model.Professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalsRepository extends JpaRepository<Professional, Long>{
    // @Query(value = "SELECT * FROM PROFESSIONALS p WHERE p.user_id = ?1", nativeQuery=true)
    // Professional findProfessionalById(Long user_id);

    @Query("SELECT p FROM Professional p WHERE p.user_id = ?1")
    public Professional findProfessionalById(Long user_id);

    @Query("SELECT p FROM Professional p WHERE p.user_name = ?1")
    public Professional findProfessionalByUsername(String user_name);

    @Query("SELECT p FROM Professional p WHERE p.email = ?1")
    public Professional findProfessionalByEmail(String email);
}