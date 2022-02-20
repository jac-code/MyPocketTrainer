package com.example.demo.service;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.model.*;

import java.util.*;

public interface ClientsService {
    public void signUpNewClient(UserDAO userDAO);
    public void saveRegisteredClient(Client client);
    public Client getClient(String verificationToken);
    public void createVerificationTokenForClient(Client client, String token);
    public VerificationToken getVerificationToken(final String VerificationToken);

    public void sendRegistrationConfirmationEmail(ModelUser user);

    public Set<Professional> listMyClients(Long id);
    public Set<Diet> listMyDiets(Long id);
    public Set<Routine> listMyRoutines(Long id);
    public Set<Exercise> listMyExercises(Long id);
}