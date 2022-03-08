package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.controller.dao.ExerciseDAO;
import com.example.demo.controller.dao.RecipeDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.Exercise;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Recipe;
import com.example.demo.repository.DietsRepository;
import com.example.demo.repository.ExercisesRepository;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.repository.RecipesRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DietService;
import com.example.demo.service.EmailService;
import com.example.demo.service.ExercisesService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RecipesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipesService{
    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private ClientsService clientsService;

    @Override
    public void saveNewRecipe(RecipeDAO recipeDAO, String user_name) {
        Recipe recipe = new Recipe();
        
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());

        recipe.setRecipe_description(recipeDAO.getRecipe_description());
        recipe.setRecipe_name(recipeDAO.getRecipe_name());
        recipe.setImage(recipeDAO.getImage());
        recipe.setProfessional(p);

        recipesRepository.save(recipe);
    }

    public List<Recipe> listRecipesByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        return recipesRepository.findRecipesByProfessional(modelUser.getUser_id());
    }

    @Override
    public Recipe getRecipeById(Long recipe_id) {
        return recipesRepository.findRecipeById(recipe_id);
    }
}