package com.example.demo.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.mail.MessagingException;

import com.example.demo.configuration.email.AccountVerificationEmailContext;
import com.example.demo.controller.dao.UserDAO;
import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Routine;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalsServiceImpl implements ProfessionalsService{
    @Autowired
    ProfessionalsRepository professionalsRepository;

    @Autowired
    ModelUserRepository modelUserRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired 
    private PasswordEncoder encoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Autowired
    private EmailService emailService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Override
    public void signUpNewProfessional(UserDAO userDAO) {
        Professional p = new Professional();

        p.setFirst_name(userDAO.getFirst_name());
        p.setLast_name(userDAO.getLast_name());
        p.setUser_name(userDAO.getUser_name());
        p.setRole(rolesRepository.findRoleByRoleDescription(userDAO.getRole_description_form()));
        p.setPassword(encoder.encode(userDAO.getPassword()));
        p.setEmail(userDAO.getEmail());
        try {
            p.setBirth_date(userDAO.getCompleteDate(userDAO.getYear(), userDAO.getMonth(), userDAO.getDay()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(emailExists(p.getEmail()) || usernameExists(p.getUser_name())) {
            throw new UserAlreadyExistsException("There is an account with that email address: " + p.getEmail());
        }

        professionalsRepository.save(p);
        sendRegistrationConfirmationEmail(p);
    }

    @Override
    public void sendRegistrationConfirmationEmail(ModelUser user) {
        VerificationToken secureToken = verificationTokenService.createSecureToken();
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
    public Set<Client> listMyClients(Long id) {
        return professionalsRepository.getById(id).getClients();
    }

    @Override
    public Set<Diet> listMyDiets(Long id) {
        return professionalsRepository.getById(id).getDiets();
    }

    @Override
    public Set<Routine> listMyRoutines(Long id) {
        return professionalsRepository.getById(id).getRoutines();
    }
}