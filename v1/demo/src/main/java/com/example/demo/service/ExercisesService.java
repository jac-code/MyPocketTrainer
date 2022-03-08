package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.controller.dao.ExerciseDAO;
import com.example.demo.model.Exercise;

public interface ExercisesService {
    public void saveNewExercise(ExerciseDAO exerciseDAO, String user_name) throws IOException;
    public List<Exercise> listExercisesByProfessional(String user_name);
    public Exercise getExerciseById(Long exercise_id);
}
