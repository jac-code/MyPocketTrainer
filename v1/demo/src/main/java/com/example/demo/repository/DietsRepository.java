package com.example.demo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.demo.model.Diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DietsRepository extends JpaRepository<Diet, Long>{
    @Query(value = "SELECT * FROM Diets d WHERE d.professional_id = ?1", nativeQuery = true)
    List<Diet> findDietsByProfessional(Long professional_id);
}