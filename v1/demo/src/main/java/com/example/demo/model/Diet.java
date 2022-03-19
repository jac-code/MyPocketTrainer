package com.example.demo.model;

import java.util.List;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "DIETS")
@Setter
@Getter
@NoArgsConstructor
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_id")
    private Long diet_id;

    @Column(name = "diet_name")
    private String diet_name;

    @Column(name = "diet_description")
    private String diet_description;

    @ManyToMany
    @JoinTable(
    name = "DIET_RECIPE",
    joinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id"), 
    inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id"))
    private List<Recipe> recipes;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    public void linkRecipeToDiet(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public void deleteRecipeFromDiet(Recipe recipe) {
        this.recipes.remove(recipe);
    }
}