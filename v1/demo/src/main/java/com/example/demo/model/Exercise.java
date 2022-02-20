package com.example.demo.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "EXERCISES")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exercise_id;

    @Column(name = "exercise_url")
    private String exercise_url;

    // si quitamos esto es UNIDIRECCIONAL --> lo que quiero
    // @ManyToMany(mappedBy = "exercises")
    // Set<Routine> routines;
}
