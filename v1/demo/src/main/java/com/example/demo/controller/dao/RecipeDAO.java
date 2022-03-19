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
public class RecipeDAO {
    private String recipe_name;
    private String recipe_description;
    private List<String> equipment;
    private List<String> ingredients;
    private String cooking_time;
    private String price;
    private String calories;
    private String protein;
    private String fat;
    private String carbs;
    private String type;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private MultipartFile image;
}