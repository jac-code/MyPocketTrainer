package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.util.List;

import lombok.*;

@Entity
@Table(name = "PROFESSIONALS")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(referencedColumnName="user_id")
public class Professional extends ModelUser{
    @Column(name = "work_zone", nullable = true)
    private String work_zone;

    @Column(name="rating", nullable = true)
    private int rating;

    @Column(name="public", nullable = true)
    private boolean isPublic;

    @ManyToMany
    @JoinTable( 
        name = "PROFESSIONALS_CLIENTS", 
        joinColumns = @JoinColumn(name = "professional_id", referencedColumnName = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName = "user_id")
    )
    private List<Client> clients;

    // owning side
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routine> routines;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diet> diets;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> dailies;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Weekly> weeklies;

    /*************** WEEKLY ***************/

    public void linkDaily(Weekly weekly) {
        this.weeklies.add(weekly);
    }

    public void removeLinkedDaily(Weekly weekly) {
        this.weeklies.remove(weekly);
    }

    /*************** DAILY ***************/

    public void linkDaily(Daily daily) {
        this.dailies.add(daily);
    }

    public void removeLinkedDaily(Daily daily) {
        this.dailies.remove(daily);
    }
    
    /*************** CLIENTS ***************/

    public void linkClient(Client client) {
        this.clients.add(client);
    }

    public void removeLinkedClient(Client client) {
        this.clients.remove(client);
    }

    /*************** DIETS ***************/
    
    public void addDiet(Diet diet) {
        this.diets.add(diet);
    }

    public void deleteDiet(Diet diet) {
        this.diets.remove(diet);
    }

    /*************** ROUTINES ***************/

    public void addRoutine(Routine routine) {
        this.routines.add(routine);
    }

    public void deleteRoutine(Routine routine) {
        this.routines.remove(routine);
    }

    /*************** EXERCISES ***************/

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public void deleteExercise(Exercise exercise) {
        this.exercises.remove(exercise);
    }

    /*************** RECIPES ***************/

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
    }
}