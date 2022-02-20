package com.example.demo.controller.dao;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DietDAO {
    @NotEmpty(message = "Diet name can not be empty")
    private String diet_name;

    @NotEmpty(message = "Diet description can not be empty")
    private String diet_description;

    // for the diet creation form
    public DietDAO(String diet_name, String diet_description) {
        this.diet_name = diet_name;
        this.diet_description = diet_description;
    }
}