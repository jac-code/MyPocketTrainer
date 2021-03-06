package com.example.demo.repository;

import com.example.demo.model.ModelUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelUserRepository extends JpaRepository<ModelUser, Long>{
    @Query("SELECT u FROM ModelUser u WHERE u.email = ?1")
    public ModelUser findModelUserByEmail(String email);

    @Query("SELECT u FROM ModelUser u WHERE u.user_name = ?1")
    public ModelUser findModelUserByUserName(String user_name);

    @Query("SELECT u FROM ModelUser u WHERE u.user_id = ?1")
    public ModelUser findModelUserById(Long user_id);
}