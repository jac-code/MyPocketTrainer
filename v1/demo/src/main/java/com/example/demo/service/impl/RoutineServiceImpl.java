package com.example.demo.service.impl;

import java.util.List;
import java.util.Set;

import com.example.demo.controller.dao.RoutineDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Exercise;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Routine;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.repository.RoutinesRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.ExercisesService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RoutineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutineServiceImpl implements RoutineService{
    @Autowired
    private RoutinesRepository routinesRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private ExercisesService exercisesService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private ClientsService clientsService;

    @Override
    public void saveNewRoutine(RoutineDAO routineDAO, String user_name) {
        Routine routine = new Routine();
        
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());
        
        
        routine.setRoutine_name(routineDAO.getRoutine_name());
        routine.setRoutine_description(routineDAO.getRoutine_description());
        routine.setProfessional(p);

        routinesRepository.save(routine);
    }

    @Override
    public Routine getRoutineById(Long routine_id) {
        Routine r = routinesRepository.findRoutineById(routine_id);
        for(Exercise e : r.getExercises()) {
            e.setImageBase64(e.getImage());
        }
        return r;
    }

    @Override
	public List<Routine> listRoutinesByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        return routinesRepository.findRoutinesByProfessional(modelUser.getUser_id());
	}

    @Override
	public List<Routine> listLinkedRoutines(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Client client = clientsService.getClientByUsername(modelUser.getUser_name());
        return client.getLinkedRoutines();
	}

    @Override
    public void ExerciseLinksWithRoutine(String routine_id, String exercise_id, String professional_user_name) {
        Professional professional = professionalsService.getProfessionalByUsername(professional_user_name);
        Routine routine = routinesRepository.findRoutineById(Long.parseLong(routine_id));
        Exercise exercise = exercisesService.getExerciseById(Long.parseLong(exercise_id));
        
        routine.linkExerciseToRoutine(exercise);
        routinesRepository.save(routine);
    }

    @Override
    public List<Routine> listFollowedRoutines(String user_name) {
        Client client = clientsService.getClientByUsername(user_name);
        return client.getFollowed_routines();
    }
}
