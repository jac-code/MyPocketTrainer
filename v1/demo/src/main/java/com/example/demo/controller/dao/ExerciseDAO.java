package com.example.demo.controller.dao;

import java.util.List;

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
    private List<String> parts;
    private List<String> equipment;
    private String number_repetitions;
    private String resting_time;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private MultipartFile image;
}