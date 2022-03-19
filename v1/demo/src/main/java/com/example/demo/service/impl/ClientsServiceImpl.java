package com.example.demo.service.impl;

import java.text.ParseException;
import java.util.*;

import javax.mail.MessagingException;

import com.example.demo.configuration.email.AccountVerificationEmailContext;
import com.example.demo.controller.dao.ClientDAO;
import com.example.demo.controller.dao.ProfessionalDAO;
import com.example.demo.controller.dao.UserDAO;
import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.model.*;
import com.example.demo.repository.ClientsRepository;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DietService;
import com.example.demo.service.EmailService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RoutineService;
import com.example.demo.service.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientsServiceImpl implements ClientsService{
    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private RoutineService routineService;

    @Autowired
    private DietService dietService;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Autowired
    private EmailService emailService;

    /************************************************* */
    /********************* TOOLS ********************* */
    /************************************************* */

    @Override
    public List<Client> listClientsByProfessional(String user_name_professional) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name_professional);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());
        return p.getClients();
	}

    @Override
    public Client getClientByUsername(String user_name) {
        return clientsRepository.findClientByUsername(user_name);
    }

    @Override
    public Client getClientById(Long id) {
        return clientsRepository.findClientById(id);
    }

    @Override
    public void saveRegisteredClient(Client client) {
        clientsRepository.save(client);
    }

    @Override
    public void ClientLinksWithProfessional(ProfessionalDAO professionalDAO, String user_name_client) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name_client);
        Client actual_client = getClientById(modelUser.getUser_id());
        Professional to_link_professional = professionalsService.getProfessionalByUsername(professionalDAO.getUser_name());

        actual_client.linkProfessional(to_link_professional);
        clientsRepository.save(actual_client);
    }

    @Override
    public void ClientDeLinksWithProfessional(Long professional_id, String user_name_client) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name_client);
        Client actual_client = getClientById(modelUser.getUser_id());
        Professional to_link_professional = professionalsService.getProfessionalById(professional_id);

        actual_client.removeLinkedProfessional(to_link_professional);
        clientsRepository.save(actual_client);
    }

    @Override
    public void ClientFollowsDiets(String diet_id, String client_user_name) {
        Client client = clientsRepository.findClientByUsername(client_user_name);
        Diet diet = dietService.getDietById(Long.parseLong(diet_id));
        client.addFollowedDiet(diet);
        clientsRepository.save(client);
    }

    @Override
    public void ClientFollowsRoutines(String routine_id, String client_user_name) {
        Client client = clientsRepository.findClientByUsername(client_user_name);
        Routine routine = routineService.getRoutineById(Long.parseLong(routine_id));
        client.addFollowedRoutine(routine);
        clientsRepository.save(client);
    }

    private boolean emailExists(String email) {
        return modelUserService.getModelUserByEmail(email) != null;
    }

    private boolean usernameExists(String user_name) {
        return modelUserService.getModelUserByUsername(user_name) != null;
    }

    /************************************************* */
    /************** SIGN UP PROCESS ****************** */
    /************************************************* */

    @Override
    public void signUpNewClient(UserDAO userDAO) throws UserAlreadyExistsException {
        Client c = new Client();

        c.setFirst_name(userDAO.getFirst_name());
        c.setLast_name(userDAO.getLast_name());
        c.setUser_name(userDAO.getUser_name());

        try {
            c.setBirth_date(userDAO.getCompleteDate(userDAO.getYear(), userDAO.getMonth(), userDAO.getDay()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        c.setPassword(encoder.encode(userDAO.getPassword()));
        c.setEmail(userDAO.getEmail());
        c.setRole(rolesRepository.findRoleByRoleDescription(userDAO.getRole_description_form()));

        if(emailExists(c.getEmail()) || usernameExists(c.getUser_name())) {
            throw new UserAlreadyExistsException("There is an account with that email address: " + c.getEmail());
        }

        clientsRepository.save(c);
        sendRegistrationConfirmationEmail(c);
    }

    @Override
    public void sendRegistrationConfirmationEmail(ModelUser user) {
        VerificationToken secureToken= verificationTokenService.createSecureToken();
        secureToken.setUser(user);
        verificationTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendEmail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createVerificationTokenForClient(Client client, String token) {
        // final VerificationToken myToken = new VerificationToken(token, client);
        // verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    @Override
    public Client getClient(String verificationToken) {
        Client client = (Client)verificationTokenRepository.findByToken(verificationToken).getUser();
        return client;
    }

    /************************************************* */
    /****************** ROUTINES ********************* */
    /************************************************* */

    @Override
    public void RoutineLinksToClient(String client_user_name, String routine_id, String professional_user_name) {
        Professional professional = professionalsService.getProfessionalByUsername(professional_user_name);
        Routine routine = routineService.getRoutineById(Long.parseLong(routine_id));
        Client client = clientsRepository.findClientByUsername(client_user_name);
        
        client.linkRoutine(routine);
        clientsRepository.save(client);
    }

    /************************************************* */
    /****************** DIETS ********************* */
    /************************************************* */

    @Override
    public void DietLinksToClient(String client_user_name, String diet_id, String professional_user_name) {
        Professional professional = professionalsService.getProfessionalByUsername(professional_user_name);
        Diet diet = dietService.getDietById(Long.parseLong(diet_id));
        Client client = clientsRepository.findClientByUsername(client_user_name);
        
        client.linkedDiet(diet);
        clientsRepository.save(client);
    }

}