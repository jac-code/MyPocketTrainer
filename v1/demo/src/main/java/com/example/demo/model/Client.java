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

    @ManyToMany(mappedBy = "clients")
    private Set<Professional> professionals;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_RECIPES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id"))
    Set<Recipe> recipes;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_DIETS",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "diet_id", referencedColumnName = "diet_id"))
    Set<Diet> diets;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_EXERCISES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id"))
    Set<Exercise> exercises;

    @ManyToMany
    @JoinTable(
    name = "FAVOURITE_CLIENT_ROUTINES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id"))
    Set<Routine> routines;

    public Client() {

    }

    // añadimos un profesional a Set profesionales
    // añadimos el cliente en Set del profesional
    // bidireccional
    public void addProfessional(Professional p) {
        this.professionals.add(p);
        p.getClients().add(this);
    }

    public void removeProfessional(Professional p) {
        this.getProfessionals().remove(p);
        p.getClients().remove(this);
    }

    public void removeAllProfessionals() {
        for (Professional p : new HashSet<>(professionals)) {
            removeProfessional(p);
        }
    }
}