package com.example.demo.controller.dao;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDAO {
    private String exercise_name;
    private String exercise_description;
    private String repetitions;
    private String body_part;
    private String tools;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private MultipartFile image;
}