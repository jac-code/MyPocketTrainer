package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.model.ModelUser;
import com.example.demo.repository.ModelUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Autowired
    ModelUserRepository modelUserRepository;

    @Override
    public UserDetails loadUserByUsername(String parameter) throws UsernameNotFoundException {
        // we authenticate with user_name
        final ModelUser customer = modelUserRepository.findModelUserByUserName(parameter);
        if (customer == null) {
            throw new UsernameNotFoundException(parameter);
        }

        // with Spring Security we can disable the signin for disabled accounts
        boolean enabled = !customer.isEnabled();

        UserDetails user = User.withUsername(customer.getUser_name()).password(customer.getPassword()).authorities(getAuthorities(customer.getRole().getRole_description())).build();
        return user;

        // return new User(customer.getUser_name(), customer.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(customer.getRole().getRole_description()));

        // if (validateEmail(parameter)) {
        //     // we authenticate with email
        //     final ModelUser customer = modelUserRepository.findModelUserByEmail(parameter);
        //     if (customer == null) {
        //         throw new UsernameNotFoundException(parameter);
        //     }

        //     // with Spring Security we can disable the signin for disabled accounts
        //     boolean enabled = !customer.isEnabled();

        //     return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(customer.getRole().getRole_description()));
        // }
        // else {
        //     // we authenticate with user_name
        //     final ModelUser customer = modelUserRepository.findModelUserByUserName(parameter);
        //     if (customer == null) {
        //         throw new UsernameNotFoundException(parameter);
        //     }

        //     // with Spring Security we can disable the signin for disabled accounts
        //     boolean enabled = !customer.isEnabled();

        //     return new org.springframework.security.core.userdetails.User(customer.getUser_name(), customer.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(customer.getRole().getRole_description()));
        // }
    }

    private static Collection<GrantedAuthority> getAuthorities (String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        
        return authorities;
    }

    private boolean validateEmail(final String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}