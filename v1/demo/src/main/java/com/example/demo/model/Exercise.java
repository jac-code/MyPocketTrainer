package com.example.demo.model;

import java.util.Base64;
import java.util.List;

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
    private String imageBase64;

    @Column(name = "body_part")
    private List<String> body_parts;

    @Column(name = "tools")
    private List<String> tools;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    // si quitamos esto es UNIDIRECCIONAL --> lo que quiero
    // @ManyToMany(mappedBy = "exercises")
    // Set<Routine> routines;

    public void setImageBase64(byte[] image) {
        this.imageBase64 = Base64.getEncoder().encodeToString(image);
    }
}
