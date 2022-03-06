package com.example.demo.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "EXERCISES")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exercise_id;

    @Column(name = "exercise_name")
    private String exercise_name;

    @Column(name = "exercise_description")
    private String exercise_description;

    // @Lob
    // @Basic(fetch = FetchType.LAZY)
    // @Column(name = "image")
    // private byte[] image;

    // @Column(name = "body_part")
    // private List<String> body_parts;

    // @Column(name = "tools")
    // private List<String> tools;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    // si quitamos esto es UNIDIRECCIONAL --> lo que quiero
    // @ManyToMany(mappedBy = "exercises")
    // Set<Routine> routines;
}
