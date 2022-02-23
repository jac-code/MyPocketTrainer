package com.example.demo.service;

import java.util.List;
import java.util.Set;

import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.model.Routine;

public interface RoutineService {
    public void saveNewRoutine(RoutineDAO routineDAO, String user_name);
    public List<Routine> listRoutinesByProfessional(String user_name);
}