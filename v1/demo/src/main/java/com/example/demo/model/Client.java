package com.example.demo.model;

import javax.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@PrimaryKeyJoinColumn(referencedColumnName="user_id")
public class Client extends ModelUser{

    @Column(name = "work_zone", nullable = true)
    private String work_zone;

    // @ManyToMany(mappedBy = "clients")
    // private List<Professional> professionals;

    //@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(
            name = "PROFESSIONALS_CLIENTS",
            joinColumns = {@JoinColumn(name = "client_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "professional_id", referencedColumnName = "user_id")}
    )
    private List<Professional> professionals;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_RECIPES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id"))
    private List<Recipe> recipes;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_DIETS",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id"))
    private List<Diet> diets;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_EXERCISES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id"))
    private List<Exercise> exercises;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_ROUTINES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id"))
    private List<Routine> routines;

    @ManyToMany
    @JoinTable(
    name = "LINKED_ROUTINES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id"))
    private List<Routine> linkedRoutines;

    @ManyToMany
    @JoinTable(
    name = "LINKED_DIETS",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id"))
    private List<Diet> linkedDiets;

    public Client() {

    }

    public void linkProfessional(Professional p) {
        this.professionals.add(p);
    }

    public void removeLinkedProfessional(Professional p) {
        this.professionals.remove(p);
    }

    public void linkRoutine(Routine routine) {
        this.linkedRoutines.add(routine);
    }

    public void linkedDiet(Diet diet) {
        this.linkedDiets.add(diet);
    }
}