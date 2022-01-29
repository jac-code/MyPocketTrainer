package com.example.demo.controller.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.example.demo.validation.ValidEmail;
import com.example.demo.validation.ValidPassword;
import com.example.demo.validation.ValidPasswordValueMatch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ValidPasswordValueMatch
public class UserDAO implements Serializable{
    @NotEmpty(message = "First name can not be empty")
    private String first_name;

    @NotEmpty(message = "Last name can not be empty")
    private String last_name;

    @NotEmpty(message = "Username can not be empty")
    private String user_name;

    @ValidPassword
    @NotEmpty(message = "Password can not be empty")
    private String password;

    @NotEmpty(message = "Matching Password can not be empty")
    private String matching_password;

    @ValidEmail
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @NotEmpty(message = "Year can not be empty")
    private String year;

    @NotEmpty(message = "Month can not be empty")
    private String month;

    @NotEmpty(message = "Day not be empty")
    private String day;

    @NotEmpty(message = "Plan can not be empty")
    private String role_description_form;

    // for the signup form
    public UserDAO(String user_name, String email, String password, String matching_password, String first_name, String last_name, String year, String month, String day, String role_description_form) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.matching_password = matching_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.role_description_form = role_description_form;
    }

    public Date getCompleteDate(String year, String month, String day) throws ParseException {
        return new SimpleDateFormat("yyyy-mm-dd").parse(year + "-" + month + "-" + day);
    }
}