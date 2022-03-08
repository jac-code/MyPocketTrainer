package com.example.demo.service;

import java.util.*;

import com.example.demo.controller.dao.ClientDAO;
import com.example.demo.controller.dao.UserDAO;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;

public interface ProfessionalsService {
    public void signUpNewProfessional(UserDAO userDAO);
    public void sendRegistrationConfirmationEmail(ModelUser user);
    
    public List<Professional> listProfessionalsByClient(String user_name_client);
    public void ProfessionalLinksWithClient(ClientDAO clientDAO, String user_name_professional);
    public void ProfessionalDeLinksWithClient(Long client_id, String professional_username);
    
    public Professional getProfessionalByUsername(String user_name);
    public Professional getProfessionalById(Long id);

    public void deleteDiet(Long diet_id, String user_name);
    public void deleteRoutine(Long diet_id, String user_name);
    public void deleteExercise(Long exercise_id, String user_name);
    public void deleteRecipe(Long recipe_id, String user_name);
}