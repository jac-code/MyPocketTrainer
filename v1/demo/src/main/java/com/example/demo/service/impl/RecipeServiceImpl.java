package com.example.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.controller.dao.ExerciseDAO;
import com.example.demo.controller.dao.RecipeDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.Exercise;
import com.example.demo.model.Ingredients;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Recipe;
import com.example.demo.model.Tools;
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
    public List<Recipe> listFollowedRecipes(String user_name) {
        Client c = clientsService.getClientByUsername(user_name);
        return c.getFollowed_recipes();
    }

    @Override
    public void saveNewRecipe(RecipeDAO recipeDAO, String user_name) {
        Recipe recipe = new Recipe();
        
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());

        recipe.setRecipe_description(recipeDAO.getRecipe_description());
        recipe.setRecipe_name(recipeDAO.getRecipe_name());
        recipe.setIngredients(recipeDAO.getIngredients());
        recipe.setTools(recipeDAO.getTools());
        
        try {
            recipe.setImage(recipeDAO.getImage().getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        recipe.setCalories(recipeDAO.getCalories());
        recipe.setCarbs(recipeDAO.getCarbs());
        recipe.setCooking_time(recipeDAO.getCooking_time());
        recipe.setFat(recipeDAO.getFat());
        recipe.setPrice(recipeDAO.getPrice());
        recipe.setProtein(recipeDAO.getProtein());
        recipe.setRecipe_type(recipeDAO.getType());
        recipe.setProfessional(p);

        recipesRepository.save(recipe);
    }

    public List<Recipe> listRecipesByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        List <Recipe> recipes = recipesRepository.findRecipesByProfessional(modelUser.getUser_id());
        for (Recipe recipe : recipes) {
            recipe.setImageBase64(recipe.getImage());
        }
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long recipe_id) {
        Recipe r = recipesRepository.findRecipeById(recipe_id);
        r.setImageBase64(r.getImage());
        return r;
    }
}