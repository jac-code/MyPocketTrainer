package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Recipe;
import com.example.demo.repository.DietsRepository;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DietService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RecipesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietServiceImpl implements DietService{
    @Autowired
    private DietsRepository dietsRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private ClientsService clientsService;

    @Override
    public void saveNewDiet(DietDAO dietDAO, String user_name) {
        Diet diet = new Diet();
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());
        
        diet.setDiet_name(dietDAO.getDiet_name());
        diet.setDiet_description(dietDAO.getDiet_description());
        diet.setProfessional(p);

        dietsRepository.save(diet);
    }

    @Override
    public Diet getDietById(Long diet_id) {
        Diet d = dietsRepository.findDietById(diet_id);
        
        for(Recipe r : d.getRecipes()) {
            r.setImageBase64(r.getImage());
        }

        return d;
    }

    @Override
    public List<Diet> listDietsByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        return dietsRepository.findDietsByProfessional(modelUser.getUser_id());
    }

    @Override
	public List<Diet> listLinkedDiets(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Client client = clientsService.getClientByUsername(modelUser.getUser_name());
        return client.getLinkedDiets();
	}

    @Override
    public void RecipesLinksWithDiet(String diet_id, String recipe_id, String professional_user_name) {
        Professional professional = professionalsService.getProfessionalByUsername(professional_user_name);
        Diet diet = dietsRepository.findDietById(Long.parseLong(diet_id));
        Recipe recipe = recipesService.getRecipeById(Long.parseLong(recipe_id));
        
        diet.linkRecipeToDiet(recipe);
        dietsRepository.save(diet);
    }

    @Override
    public List<Diet> listFollowedDiets(String user_name) {
        Client client = clientsService.getClientByUsername(user_name);
        return client.getFollowed_diets();
    }
}