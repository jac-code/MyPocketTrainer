package com.example.demo.controller.dao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ClientDAO {
    private String email;
    private String name;
    private String user_name;

    public ClientDAO(String user_name) {
        this.user_name = user_name;
    }
}