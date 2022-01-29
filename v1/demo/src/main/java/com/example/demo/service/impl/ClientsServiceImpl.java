package com.example.demo.service.impl;

import java.text.ParseException;
import java.util.Objects;

import javax.mail.MessagingException;

import com.example.demo.configuration.email.AccountVerificationEmailContext;
import com.example.demo.controller.dao.UserDAO;
import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.model.Client;
import com.example.demo.model.ModelUser;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.ClientsRepository;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.EmailService;
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
    private ModelUserRepository modelUserRepository;

    @Autowired
    private RolesRepository rolesRepository;

    // @Autowired
    // PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Autowired
    private EmailService emailService;

    @Override
    public void saveMeal(String meal_url) {
        
    }

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

    private boolean emailExists(String email) {
        return modelUserRepository.findModelUserByEmail(email) != null;
    }

    private boolean usernameExists(String user_name) {
        return modelUserRepository.findModelUserByUserName(user_name) != null;
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

    @Override
    public void saveRegisteredClient(Client client) {
        clientsRepository.save(client);
    }
}