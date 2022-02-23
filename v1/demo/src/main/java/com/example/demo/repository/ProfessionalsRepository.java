package com.example.demo.repository;

import com.example.demo.model.Professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalsRepository extends JpaRepository<Professional, Long>{
    // @Query("SELECT p FROM Professional p WHERE p.status = ?1")
    // Professional findByUsername(String user_name);

    // @Query(value = "SELECT * FROM PROFESSIONALS p WHERE p.user_id = ?1", nativeQuery=true)
    // Professional findProfessionalById(Long user_id);

    @Query("SELECT u FROM Professional u WHERE u.user_id = ?1")
    public Professional findProfessionalById(Long user_id);

    // @Query("SELECT p FROM Professional p WHERE p.professional_id = ?1")
    // Professional findProfessionalById(Long professional_id);

    // @Query("SELECT u FROM User u WHERE u.email = ?1")
    // public User findUserByEmail(String email);

    // @Query("SELECT u FROM User u WHERE u.user_name = ?1")
    // public User findUserByUserName(String user_name);
}