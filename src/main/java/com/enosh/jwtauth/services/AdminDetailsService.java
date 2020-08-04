package com.enosh.jwtauth.services;

import com.enosh.jwtauth.model.AdminDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(email)
                .filter(username -> username.equals("admin"))
                .map(username -> new AdminDetails())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Wrong username or password"
                ));
    }
}
