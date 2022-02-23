package com.example.demo;

import com.example.demo.controller.dao.UserDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Role;
import com.example.demo.model.Professional;
import com.example.demo.repository.ClientsRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.ProfessionalsService;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserTestRepository {
    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ProfessionalsRepository professionalsRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void Test_Create_Client_Jaime() throws ParseException {
        Client client = new Client();
        client.setFirst_name("Jaime");
        client.setLast_name("Arana Cardelús");
        client.setUser_name("jarana");
        client.setPassword("JaimeArana2020#");
        client.setEmail("jaime.aracarde@gmail.com");
        client.setRole(rolesRepository.findRoleByRoleDescription("ROLE_CLIENT_FREE"));

        client.setBirth_date(new SimpleDateFormat("yyyy-mm-dd").parse("2000-05-24"));

        Client savedClient = clientsRepository.saveAndFlush(client);

        Client existClient = entityManager.find(Client.class, savedClient.getUser_id());
        
        assertThat(client.getUser_name()).isEqualTo(existClient.getUser_name());
    }

    @Test
    public void Test_Create_Client_Paula() throws ParseException {
        UserDAO userDAO = new UserDAO();
        userDAO.setDay("12");
        userDAO.setMonth("03");
        userDAO.setYear("2001");
        userDAO.setFirst_name("Paula");
        userDAO.setLast_name("del Castillo Ventura");
        userDAO.setUser_name("pauladelcas");
        userDAO.setPassword("PaulaDelCastillo2020#");
        userDAO.setEmail("pauladelcastillo@gmail.com");
        userDAO.setRole_description_form("ROLE_PROFESSIONAL_BASIC");

        // professionalsService.signUpNewProfessional(userDAO);

        Professional p = new Professional();

        p.setFirst_name(userDAO.getFirst_name());
        p.setLast_name(userDAO.getLast_name());
        p.setUser_name(userDAO.getUser_name());
        p.setRole(rolesRepository.findRoleByRoleDescription(userDAO.getRole_description_form()));
        p.setPassword((userDAO.getPassword()));
        p.setEmail(userDAO.getEmail());
        try {
            p.setBirth_date(userDAO.getCompleteDate(userDAO.getYear(), userDAO.getMonth(), userDAO.getDay()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Professional p_saved = professionalsRepository.save(p);
        Professional p_exist = entityManager.find(Professional.class, p_saved.getUser_id());

        assertThat(p.getUser_name()).isEqualTo(p_exist.getUser_name());
    }

    @Test
    public void Test_Create_Trainer_Rocky() throws ParseException {
        // ('Sylvester', 'Stallone', 'rockybalboa', '$2a$10$NZ9Os7lB.waU0nWUVRdNIOB7PMdOjfl2JeYqVGNviqm1zT4t24Vnm', 'rocky.balboa@gmail.com', DATE '1960-03-03', 3, TRUE),
        Professional professional = new Professional();
        professional.setFirst_name("Sylvester");
        professional.setLast_name("Stallone");
        professional.setUser_name("rockybalboa");
        professional.setPassword("RockyBalboa2020#");
        professional.setEmail("rocky.balboa@gmail.com");
        professional.setBirth_date(new SimpleDateFormat("yyyy-mm-dd").parse("1960-01-11"));
        professional.setRating(2);
        professional.setRole(rolesRepository.findRoleByRoleDescription("ROLE_PROFESSIONAL_BASIC"));
        
        Professional savedProfessional = professionalsRepository.save(professional);
        Professional existTrainer = entityManager.find(Professional.class, savedProfessional.getUser_id());
        
        assertThat(savedProfessional.getUser_name()).isEqualTo(existTrainer.getUser_name());
    }

    @Test
    public void Test_Create_Trainer_Terminator() throws ParseException {        
        Professional professional = new Professional();
        professional.setFirst_name("Arnold");
        professional.setLast_name("Schwarzernegger");
        professional.setUser_name("terminator");
        professional.setPassword("terminator");
        professional.setEmail("terminator@gmail.com");
        professional.setBirth_date(new SimpleDateFormat("yyyy-mm-dd").parse("1962-03-20"));
        professional.setRating(2);
        professional.setRole(rolesRepository.findRoleByRoleDescription("ROLE_PROFESSIONAL_BUSINESS"));
        
        Professional savedProfessional = professionalsRepository.save(professional);
        
        Professional existTrainer = entityManager.find(Professional.class, savedProfessional.getUser_id());
        
        assertThat(savedProfessional.getUser_name()).isEqualTo(existTrainer.getUser_name());
    }
}
