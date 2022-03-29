package com.example.demo.controller.dao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RoutineDAO {
    private String routine_name;
    private String routine_description;

    // for the diet creation form
    public RoutineDAO(String routine_name, String routine_description) {
        this.routine_name = routine_name;
        this.routine_description = routine_description;
    }
}
