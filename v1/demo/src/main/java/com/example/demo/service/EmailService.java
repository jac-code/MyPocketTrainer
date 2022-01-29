package com.example.demo.service;

import com.example.demo.configuration.email.AbstractEmailContext;
import javax.mail.MessagingException;

public interface EmailService {
    public void sendEmail(final AbstractEmailContext email) throws MessagingException;
}
