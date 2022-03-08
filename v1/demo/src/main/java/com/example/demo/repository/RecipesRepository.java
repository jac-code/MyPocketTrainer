package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long>{
    @Query("SELECT r FROM Recipe r WHERE r.recipe_id = ?1")
    public Recipe findRecipeById(Long recipe_id);

    @Query(value = "SELECT * FROM Recipes r WHERE r.professional_id = ?1", nativeQuery = true)
    public List<Recipe> findRecipesByProfessional(Long professional_id);
}