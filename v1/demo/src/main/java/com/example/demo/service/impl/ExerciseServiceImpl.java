package com.example.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.controller.dao.ExerciseDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.Exercise;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.repository.DietsRepository;
import com.example.demo.repository.ExercisesRepository;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DietService;
import com.example.demo.service.EmailService;
import com.example.demo.service.ExercisesService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExercisesService{
    @Autowired
    private ExercisesRepository exercisesRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private ClientsService clientsService;

    @Override
    public void saveNewExercise(ExerciseDAO exerciseDAO, String user_name) {
        Exercise exercise = new Exercise();
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());

        exercise.setExercise_description(exerciseDAO.getExercise_description());
        exercise.setExercise_name(exerciseDAO.getExercise_name());
        exercise.setTools(exerciseDAO.getTools());
        exercise.setBody_parts(exerciseDAO.getBody_part());

        try {
            exercise.setImage(exerciseDAO.getImage().getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        exercise.setProfessional(p);

        exercisesRepository.save(exercise);
    }

    @Override
    public Exercise getExerciseById(Long exercise_id) {
        return exercisesRepository.findExerciseByExerciseId(exercise_id);
    }

    @Override
    public List<Exercise> listExercisesByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        List <Exercise> exercises = exercisesRepository.findExercisesByProfessional(modelUser.getUser_id());
        for (Exercise exercise : exercises) {
            exercise.setImageBase64(exercise.getImage());
        }
        
        return exercises;
    }
}