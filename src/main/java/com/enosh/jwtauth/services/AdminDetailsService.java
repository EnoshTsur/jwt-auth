package com.enosh.jwtauth.services;

import com.enosh.jwtauth.model.AdminDetails;
import com.enosh.jwtauth.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Value("${admin-username}")
    private String username;

    @Value("${admin-password}")
    private String password;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(email)
                .filter(subject -> subject.equals(username))
                .map(subject -> new AdminDetails(
                        new UserEntity(username, password)
                ))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Wrong username or password"
                ));
    }
}
