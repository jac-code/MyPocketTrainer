package com.example.demo.service;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.model.Client;
import com.example.demo.model.ModelUser;
import com.example.demo.model.VerificationToken;

public interface ClientsService {
    public void signUpNewClient(UserDAO userDAO);
    public void saveRegisteredClient(Client client);
    public Client getClient(String verificationToken);
    public void createVerificationTokenForClient(Client client, String token);
    public VerificationToken getVerificationToken(final String VerificationToken);

    public void sendRegistrationConfirmationEmail(ModelUser user);

    public void saveMeal(String meal_url);
}