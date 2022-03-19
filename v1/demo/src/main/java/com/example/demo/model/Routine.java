package com.example.demo.model;

import java.util.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "ROUTINES")
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Long routine_id;

    @Column(name = "routine_name")
    private String routine_name;

    @Column(name = "routine_description")
    private String routine_description;

    @ManyToMany
    @JoinTable(
            name = "ROUTINE_EXERCISE",
            joinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id"), 
            inverseJoinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id")
    )
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    public void linkExerciseToRoutine(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public void deleteExerciseFromRoutine(Exercise exercise) {
        this.exercises.remove(exercise);
    }
}