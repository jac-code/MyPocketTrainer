package com.example.demo.controller.dao;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
public class UserFinderDAO {

    private String parameter;
    private String user_type;

    public UserFinderDAO(String parameter, String user_type) {
        this.parameter = parameter;
        this.user_type = user_type;
    }
}
