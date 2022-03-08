package com.example.demo.controller.dao;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDAO {
    @NotEmpty(message = "Diet name can not be empty")
    private String recipe_name;

    @NotEmpty(message = "Diet name can not be empty")
    private String recipe_description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    public RecipeDAO(String recipe_name, String recipe_description, byte[] image) {
        this.recipe_name = recipe_name;
        this.recipe_description = recipe_description;
        this.image = image;
    }
}