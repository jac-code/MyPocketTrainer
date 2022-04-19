package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Daily;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailiesRepository extends JpaRepository<Daily, Long>{
    @Query(value = "SELECT * FROM Daily d WHERE d.professional_id = ?1", nativeQuery = true)
    List<Daily> findDailiesByProfessional(Long professional_id);

    @Query("SELECT d FROM Daily d WHERE d.daily_id = ?1")
    public Daily findDailyById(Long daily_id);
}