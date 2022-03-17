package com.example.demo.controller.dao;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDAO {
    @NotEmpty(message = "Diet name can not be empty")
    private String exercise_name;

    @NotEmpty(message = "Diet name can not be empty")
    private String exercise_description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private MultipartFile image;

    private String body_part;
    private String tools;

    public ExerciseDAO(String exercise_name, String exercise_description, MultipartFile image, List<String> body_parts, List<String> tools) {
        this.exercise_name = exercise_name;
        this.exercise_description = exercise_description;
        this.image = image;
    }
}