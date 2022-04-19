package com.example.demo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.demo.model.Weekly;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekliesRepository extends JpaRepository<Weekly, Long>{
    @Query(value = "SELECT * FROM Weekly w WHERE w.professional_id = ?1", nativeQuery = true)
    public List<Weekly> findByProfessional(Long professional_id);

    @Query("SELECT w FROM Weekly w WHERE w.weekly_id = ?1")
    public Weekly findWeeklyById(Long weekly_id);
}