package com.example.demo.service.impl;

import java.util.List;
import java.util.Set;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.model.Diet;
import com.example.demo.repository.DietsRepository;
import com.example.demo.service.DietService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietServiceImpl implements DietService{
    @Autowired
    private DietsRepository dietsRepository;

    @Override
    public void saveNewDiet(DietDAO dietDAO) {
        Diet diet = new Diet();
        
        diet.setDiet_name(dietDAO.getDiet_name());
        diet.setDiet_description(dietDAO.getDiet_description());

        dietsRepository.save(diet);
    }

    @Override
    public List<Diet> listDietsByProfessional() {
        // return dietsRepository.findDietsByProfessional(professional_id);
        return dietsRepository.findAll();
    }
}
