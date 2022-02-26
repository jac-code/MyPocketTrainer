package com.example.demo.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.mail.MessagingException;

import com.example.demo.configuration.email.AccountVerificationEmailContext;
import com.example.demo.controller.dao.ClientDAO;
import com.example.demo.controller.dao.ProfessionalDAO;
import com.example.demo.controller.dao.UserDAO;
import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.model.Client;
import com.example.demo.model.Diet;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Routine;
import com.example.demo.model.VerificationToken;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalsServiceImpl implements ProfessionalsService{
    @Autowired
    ProfessionalsRepository professionalsRepository;

    @Autowired
    DietService dietService;

    @Autowired
    RoutineService routineService;

    @Autowired
    ClientsService clientsService;

    @Autowired
    ModelUserService modelUserService;

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

    /************************************************* */
    /************** SIGN UP PROCESS ****************** */
    /************************************************* */

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
        sendRegistrationConfirmationEmail((ModelUser)p);
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

    /************************************************* */
    /********************* TOOLS ********************* */
    /************************************************* */
    
    private boolean emailExists(String email) {
        return modelUserService.getModelUserByEmail(email) != null;
    }

    private boolean usernameExists(String user_name) {
        return modelUserService.getModelUserByUsername(user_name) != null;
    }

    @Override
    public List<Professional> listProfessionalsByClient(String user_name_client) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name_client);
        Client c = clientsService.getClientById(modelUser.getUser_id());
        return c.getProfessionals();
	}

    @Override
    public void ProfessionalLinksWithClient(ClientDAO clientDAO, String user_name_professional) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name_professional);
        Professional actual_professional = getProfessionalById(modelUser.getUser_id());
        Client to_link_client = clientsService.getClientByUsername(clientDAO.getUser_name());

        actual_professional.linkClient(to_link_client);
        professionalsRepository.save(actual_professional);
    }

    @Override
    public void ProfessionalDeLinksWithClient(Long client_id, String professional_username) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(professional_username);
        Professional actual_professional = getProfessionalById(modelUser.getUser_id());
        Client to_link_client = clientsService.getClientById(client_id);

        actual_professional.removeLinkedClient(to_link_client);
        professionalsRepository.save(actual_professional);
    }

    @Override
    public Professional getProfessionalByUsername(String user_name) {
        return professionalsRepository.findProfessionalByUsername(user_name);
    }

    @Override
    public Professional getProfessionalById(Long id) {
        return professionalsRepository.findProfessionalById(id);
    }

    @Override
    public void deleteDiet(Long diet_id, String user_name) {
        Diet to_delete_diet =  dietService.getDietById(diet_id);
        Professional professional = professionalsRepository.findProfessionalByUsername(user_name);
        professional.deleteDiet(to_delete_diet);
        professionalsRepository.save(professional);
    }

    @Override
    public void deleteRoutine(Long routine_id, String user_name) {
        Routine to_delete_routine =routineService.getRoutineById(routine_id);
        Professional professional = professionalsRepository.findProfessionalByUsername(user_name);
        professional.deleteRoutine(to_delete_routine);
        professionalsRepository.save(professional);
    }
}