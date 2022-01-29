package com.example.demo.service;

import com.example.demo.model.VerificationToken;

public interface VerificationTokenService {
    public VerificationToken createSecureToken();
    public void saveSecureToken(VerificationToken token);
    public VerificationToken findByToken(String token);
    public void removeToken(VerificationToken token);
    public void removeTokenByToken(String token);  
}
