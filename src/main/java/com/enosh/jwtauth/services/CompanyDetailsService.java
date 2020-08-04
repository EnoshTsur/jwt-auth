package com.enosh.jwtauth.services;

import com.enosh.jwtauth.model.CompanyDetails;
import com.enosh.jwtauth.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyDetailsService implements UserDetailsService {

    private final CompanyRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .map(CompanyDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User by the email: " + email + " does not exists"
                ));
    }
}
