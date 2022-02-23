package com.example.demo.service.impl;

import java.util.Objects;

import com.example.demo.exceptions.InvalidVerificationTokenException;
import com.example.demo.model.ModelUser;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.ModelUserRepository;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelUserServiceImpl implements ModelUserService{
    @Autowired
    ModelUserRepository modelUserRepository;

    @Autowired
    VerificationTokenService verificationTokenService;
    
    @Override
    public boolean verifyUser(String token) throws InvalidVerificationTokenException {
        VerificationToken secureToken = verificationTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !token.equals(secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidVerificationTokenException("Token is not valid");
        }
        ModelUser user = modelUserRepository.getOne(secureToken.getUser().getUser_id());
        if(Objects.isNull(user)){
            return false;
        }
        user.setEnabled(true);
        modelUserRepository.save(user); // let's same user details

        // we don't need invalid password now
        verificationTokenService.removeToken(secureToken);
        return true;
    }

    @Override
    public ModelUser getModelUserByUsername(String user_name) {
        return modelUserRepository.findModelUserByUserName(user_name);
    }
}