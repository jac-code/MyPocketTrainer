package com.example.demo.service;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.model.ModelUser;

public interface ProfessionalsService {
    public void signUpNewProfessional(UserDAO userDAO);
    public void sendRegistrationConfirmationEmail(ModelUser user);
}
