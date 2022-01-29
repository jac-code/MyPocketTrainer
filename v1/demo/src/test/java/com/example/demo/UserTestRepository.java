package com.example.demo;

import com.example.demo.model.Client;
import com.example.demo.model.Role;
import com.example.demo.model.Professional;
import com.example.demo.repository.ClientsRepository;
import com.example.demo.repository.ProfessionalsRepository;
import com.example.demo.repository.RolesRepository;

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
        Client client = new Client();
        client.setFirst_name("Paula");
        client.setLast_name("del Castillo Ventura");
        client.setUser_name("pauladelcas");
        client.setPassword("PaulaDelCastillo2020#");
        client.setEmail("pauladelcastillo@gmail.com");
        client.setBirth_date(new SimpleDateFormat("yyyy-mm-dd").parse("2001-03-12"));
        client.setRole(rolesRepository.findRoleByRoleDescription("ROLE_CLIENT_PREMIUM"));

        Client savedClient = clientsRepository.save(client);
        
        Client existClient = entityManager.find(Client.class, savedClient.getUser_id());
        
        assertThat(client.getUser_name()).isEqualTo(existClient.getUser_name());
    }

    @Test
    public void Test_Create_Trainer_Rocky() throws ParseException {
        Professional professional = new Professional();
        professional.setFirst_name("Sylvester");
        professional.setLast_name("Stallone");
        professional.setUser_name("rocky");
        professional.setPassword("rocky");
        professional.setEmail("rocky@gmail.com");
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
