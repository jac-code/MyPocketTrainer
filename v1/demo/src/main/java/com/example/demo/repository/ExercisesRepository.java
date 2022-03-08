package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Long>{
    @Query("SELECT e FROM Exercise e WHERE e.exercise_id = ?1")
    public Exercise findExerciseById(Long exercise_id);

    @Query(value = "SELECT * FROM Exercises e WHERE e.professional_id = ?1", nativeQuery = true)
    public List<Exercise> findExercisesByProfessional(Long professional_id);
}