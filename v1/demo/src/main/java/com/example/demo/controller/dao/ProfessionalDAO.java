package com.example.demo.controller.dao;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ProfessionalDAO {
    @NotEmpty(message = "Professional email can not be empty")
    private String email;

    @NotEmpty(message = "Professional name can not be empty")
    private String name;

    @NotEmpty(message = "Professional username can not be empty")
    private String user_name;

    // public ClientDAO(String name, String email) {
    //     this.name = name;
    //     this.email = email;
    // }

    // public ClientDAO(String name, String user_name) {
    //     this.name = name;
    //     this.user_name = user_name;
    // }

    // public ClientDAO(String email) {
    //     this.email = email;
    // }

    public ProfessionalDAO(String user_name) {
        this.user_name = user_name;
    }
}
