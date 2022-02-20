package com.example.demo.service;

import java.util.List;
import java.util.Set;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.model.Diet;

public interface DietService {
    public void saveNewDiet(DietDAO dietDAO);
    public List<Diet> listDietsByProfessional();
}
