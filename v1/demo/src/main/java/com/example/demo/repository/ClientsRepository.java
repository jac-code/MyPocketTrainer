package com.example.demo.repository;

import com.example.demo.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long>{
    @Query("SELECT c FROM Client c WHERE c.user_id = ?1")
    public Client findClientById(Long user_id);

    @Query("SELECT c FROM Client c WHERE c.user_name = ?1")
    public Client findClientByUsername(String user_name);

    @Query("SELECT c FROM Client c WHERE c.email = ?1")
    public Client findClientByEmail(String email);

    // dbUsers.isEmpty()
}