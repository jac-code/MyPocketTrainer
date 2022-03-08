package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.model.Diet;

public interface DietService {
    public void saveNewDiet(DietDAO dietDAO, String user_name);
    public List<Diet> listDietsByProfessional(String user_name);
    public Diet getDietById(Long diet_id);
    public List<Diet> listLinkedDiets(String user_name);
    public void RecipesLinksWithDiet(String diet_id, String recipe_id, String professional_user_name);
}
