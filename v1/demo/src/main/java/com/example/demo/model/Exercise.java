package com.example.demo.model;

import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "EXERCISES")
@Getter
@Setter
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exercise_id;

    @Column(name = "exercise_name")
    private String exercise_name;

    @Column(name = "exercise_description")
    private String exercise_description;

    @Lob    // BLOB = binary data || CLOB = text data
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    private byte[] image;

    // para poder representarla en html
    @Transient
    private String imageBase64;

    @Column(name = "body_part")
    private String body_parts;

    @Column(name = "tools")
    private String tools;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @Column(name = "number_repetitions")
    private String number_repetitions;

    @Column(name = "resting_time")
    private String resting_time;    // resting time between sets

    // si quitamos esto es UNIDIRECCIONAL --> lo que quiero
    // NOT owner
    // @ManyToMany(mappedBy = "exercises")
    // private List<Routine> routines;

    // @ManyToMany(cascade = CascadeType.REMOVE)
    // @JoinTable(
    // name = "ROUTINE_EXERCISE",
    // joinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id"),
    // inverseJoinColumns = @JoinColumn(name = "routine_id", referencedColumnName = "routine_id"))
    // private List<Routine> routines;

    public void setImageBase64(byte[] image) {
        this.imageBase64 = Base64.getEncoder().encodeToString(image);
    }
}