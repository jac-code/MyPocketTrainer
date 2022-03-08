package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.model.Routine;

public interface RoutineService {
    public void saveNewRoutine(RoutineDAO routineDAO, String user_name);
    public List<Routine> listRoutinesByProfessional(String user_name);
    public Routine getRoutineById(Long routine_id);
    public List<Routine> listLinkedRoutines(String user_name);
    public void ExerciseLinksWithRoutine(String routine_id, String exercise_id, String professional_user_name);
}