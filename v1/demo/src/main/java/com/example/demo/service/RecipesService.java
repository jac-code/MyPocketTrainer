package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dao.RecipeDAO;
import com.example.demo.model.Recipe;

public interface RecipesService {
    public void saveNewRecipe(RecipeDAO recipeDAO, String user_name);
    public List<Recipe> listRecipesByProfessional(String user_name);
    public Recipe getRecipeById(Long recipe_id);
}