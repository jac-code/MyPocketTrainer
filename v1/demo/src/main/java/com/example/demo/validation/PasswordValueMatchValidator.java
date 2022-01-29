package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.controller.dao.UserDAO;

public class PasswordValueMatchValidator implements ConstraintValidator<ValidPasswordValueMatch, Object> {

    @Override
    public void initialize(ValidPasswordValueMatch constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDAO user = (UserDAO) obj;
        return user.getPassword().equals(user.getMatching_password());
    }
}
