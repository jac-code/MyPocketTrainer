package com.example.demo.service;

import com.example.demo.exceptions.InvalidVerificationTokenException;

public interface ModelUserService {
    public boolean verifyUser(String token) throws InvalidVerificationTokenException;
}
