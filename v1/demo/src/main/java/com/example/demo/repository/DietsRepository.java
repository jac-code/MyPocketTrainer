package com.example.demo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.example.demo.model.Diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DietsRepository extends JpaRepository<Diet, Long>{
    // @Query("SELECT u FROM User u WHERE u.status = ?1")
    // Diet findUserByStatus(Integer status);

    // @Query(value = "SELECT * FROM Users u WHERE u.status = ?1", nativeQuery = true)
    // Diet findUserByStatusNative(Integer status);

    // @Query(value = "SELECT u FROM User u WHERE u.name IN :names")
    // List<Diet> findUserByNameList(@Param("names") Collection<String> names);

    // @Query("SELECT u FROM User u WHERE u.status = 1")
    // Collection<User> findAllActiveUsers();

    // @Query("SELECT d FROM Diet d WHERE d.professional_id = ?1")
    // Set<Diet> findDietsByProfessional(Long professional_id);
}