package com.example.demo.model;

import javax.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@NoArgsConstructor
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
            name = "FOLLOWED_DIETS",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id")
    )
    private List<Diet> followed_diets;

    @ManyToMany
    @JoinTable(
            name = "FOLLOWED_ROUTINES",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id")
    )
    private List<Routine> followed_routines;

    @ManyToMany
    @JoinTable(
            name = "FOLLOWED_EXERCISES",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id")
    )
    private List<Exercise> followed_exercises;

    @ManyToMany
    @JoinTable(
            name = "FOLLOWED_RECIPES",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id")
    )
    private List<Recipe> followed_recipes;

    @ManyToMany
    @JoinTable(
            name = "FOLLOWED_PLANS",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "weekly_id", referencedColumnName = "weekly_id")
    )
    private List<Weekly> followed_plans;

    @ManyToMany
    @JoinTable(
            name = "LINKED_ROUTINES",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id")
    )
    private List<Routine> linkedRoutines;

    @ManyToMany
    @JoinTable(
            name = "LINKED_DIETS",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id")
    )
    private List<Diet> linkedDiets;

    @ManyToMany
    @JoinTable(
            name = "LINKED_DAILIES",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
            inverseJoinColumns = @JoinColumn(name = "daily_id", referencedColumnName = "daily_id")
    )
    private List<Daily> linkedDailies;

    @ManyToOne
    @JoinColumn(name = "weekly_id")
    private Weekly weekly;

    public void linkLinkedDaily(Daily d) {
        this.linkedDailies.add(d);
    }

    public void removeLinkedDaily(Daily d) {
        this.linkedDailies.remove(d);
    }

    public void linkProfessional(Professional p) {
        this.professionals.add(p);
    }

    public void removeLinkedProfessional(Professional p) {
        this.professionals.remove(p);
    }

    public void addFollowedRoutine(Routine routine) {
        this.followed_routines.add(routine);
    }

    public void addFollowedDiet(Diet diet) {
        this.followed_diets.add(diet);
    }

    public void unFollowRoutine(Routine routine) {
        this.followed_routines.remove(routine);
    }

    public void unFollowDiet(Diet diet) {
        this.followed_diets.remove(diet);
    }

    public void addFollowedRecipe(Recipe recipe) {
        this.followed_recipes.add(recipe);
    }

    public void unFollowRecipe(Recipe recipe) {
        this.followed_recipes.remove(recipe);
    }

    public void addFollowedExercise(Exercise e) {
        this.followed_exercises.add(e);
    }

    public void unFollowExercise(Exercise e) {
        this.followed_exercises.remove(e);
    }

    public void addFollowedPlan(Weekly w) {
        this.followed_plans.add(w);
    }

    public void unFollowPlan(Weekly w) {
        this.followed_plans.remove(w);
    }

    public void linkRoutine(Routine routine) {
        this.linkedRoutines.add(routine);
    }

    public void linkedDiet(Diet diet) {
        this.linkedDiets.add(diet);
    }
}