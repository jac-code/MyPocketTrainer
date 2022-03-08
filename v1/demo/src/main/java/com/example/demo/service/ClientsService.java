package com.example.demo.service;

import com.example.demo.controller.dao.ClientDAO;
import com.example.demo.controller.dao.ProfessionalDAO;
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

    public List<Client> listClientsByProfessional(String user_name_professional);
    public void ClientLinksWithProfessional(ProfessionalDAO professionalDAO, String user_name_client);
    public void ClientDeLinksWithProfessional(Long professional_id, String user_name_client);
    
    public Client getClientByUsername(String user_name);
    public Client getClientById(Long id);

    public void RoutineLinksToClient(String client_user_name, String routine_id, String professional_user_name);
    public void DietLinksToClient(String client_user_name, String diet_id, String professional_user_name);
}