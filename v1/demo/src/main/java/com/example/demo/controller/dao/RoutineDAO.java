package com.example.demo.controller.dao;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RoutineDAO {
    @NotEmpty(message = "Diet name can not be empty")
    private String routine_name;

    @NotEmpty(message = "Diet description can not be empty")
    private String routine_description;

    // for the diet creation form
    public RoutineDAO(String routine_name, String routine_description) {
        this.routine_name = routine_name;
        this.routine_description = routine_description;
    }
}
