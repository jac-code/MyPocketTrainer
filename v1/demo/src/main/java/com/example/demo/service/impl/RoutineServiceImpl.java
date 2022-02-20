package com.example.demo.service.impl;

import java.util.List;
import java.util.Set;

import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.model.Routine;
import com.example.demo.repository.RoutinesRepository;
import com.example.demo.service.RoutineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutineServiceImpl implements RoutineService{
    @Autowired
    private RoutinesRepository routinesRepository;

    @Override
    public void saveNewRoutine(RoutineDAO routineDAO) {
        Routine routine = new Routine();
        
        routine.setRoutine_name(routineDAO.getRoutine_name());
        routine.setRoutine_description(routineDAO.getRoutine_description());

        routinesRepository.save(routine);
    }

    @Override
	public List<Routine> listRoutinesByProfessional() {
		return routinesRepository.findAll();
	}
}
