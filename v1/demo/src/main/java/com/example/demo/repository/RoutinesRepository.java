package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Routine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutinesRepository extends JpaRepository<Routine, Long>{
    @Query(value = "SELECT * FROM Routines r WHERE r.professional_id = ?1", nativeQuery = true)
    List<Routine> findRoutinesByProfessional(Long professional_id);

    @Query("SELECT r FROM Routine r WHERE r.routine_id = ?1")
    Routine findRoutineById(Long routine_id);
}
