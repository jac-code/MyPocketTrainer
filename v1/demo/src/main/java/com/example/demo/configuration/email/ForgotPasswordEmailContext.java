package com.example.demo.configuration.email;

import com.example.demo.model.ModelUser;
import org.springframework.web.util.UriComponentsBuilder;

public class ForgotPasswordEmailContext extends AbstractEmailContext {
    private String token;

    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        ModelUser customer = (ModelUser) context; // we pass the customer information
        put("firstName", customer.getFirst_name());
        setTemplateLocation("email-forgot-password");
        setSubject("Forgotten Password");
        setFrom("mypockettrainerteam@gmail.com");
        setTo(customer.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL).path("/password/change").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
