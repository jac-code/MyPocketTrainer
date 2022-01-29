package com.example.demo.repository;

import com.example.demo.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>{
    @Query("SELECT r FROM Role r WHERE r.id = ?1")
    public Role findRoleById(Long id);

    @Query("SELECT r FROM Role r WHERE r.role_description = ?1")
    public Role findRoleByRoleDescription(String role_description);
}