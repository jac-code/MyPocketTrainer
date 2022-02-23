package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.controller.dao.DietDAO;
import com.example.demo.model.Diet;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.repository.DietsRepository;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.service.DietService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietServiceImpl implements DietService{
    @Autowired
    private DietsRepository dietsRepository;

    @Autowired
    private ProfessionalsRepository professionalsRepository;

    @Autowired
    private ModelUserRepository modelUserRepository;

    @Override
    public void saveNewDiet(DietDAO dietDAO, String user_name) {
        Diet diet = new Diet();
        ModelUser modelUser = modelUserRepository.findModelUserByUserName(user_name);
        Professional p = professionalsRepository.findProfessionalById(modelUser.getUser_id());
        
        diet.setDiet_name(dietDAO.getDiet_name());
        diet.setDiet_description(dietDAO.getDiet_description());
        diet.setProfessional(p);

        dietsRepository.save(diet);
    }

    @Override
    public List<Diet> listDietsByProfessional(String user_name) {
        ModelUser modelUser = modelUserRepository.findModelUserByUserName(user_name);
        // Professional professional = professionalsRepository.findProfessionalById(modelUser.getUser_id());
        
        return dietsRepository.findDietsByProfessional(modelUser.getUser_id());
    }
}