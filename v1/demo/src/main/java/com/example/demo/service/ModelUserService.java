package com.example.demo.service;

import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.model.ModelUser;

public interface ModelUserService {
    public boolean verifyUser(String token) throws InvalidVerificationTokenException;

    public ModelUser getModelUserByUsername(String user_name);
    public ModelUser getModelUserById(Long id);
    public ModelUser getModelUserByEmail(String email);
}
