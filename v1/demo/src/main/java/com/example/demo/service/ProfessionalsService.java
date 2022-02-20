package com.example.demo.service;

import java.util.*;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Routine;

public interface ProfessionalsService {
    public void signUpNewProfessional(UserDAO userDAO);
    public void sendRegistrationConfirmationEmail(ModelUser user);

    public Set<Client> listMyClients(Long id);
    public Set<Diet> listMyDiets(Long id);
    public Set<Routine> listMyRoutines(Long id);
}