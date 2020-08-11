package com.enosh.jwtauth.controller;

import com.enosh.jwtauth.model.LoginDto;
import com.enosh.jwtauth.model.Scope;
import com.enosh.jwtauth.repository.CompanyRepository;
import com.enosh.jwtauth.services.CompanyDetailsService;
import com.enosh.jwtauth.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.enosh.jwtauth.model.Scope.*;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final AuthenticationManager authenticationManager;
    private final CompanyDetailsService companyDetailsService;
    private final CompanyRepository companyRepository;
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }

        return ResponseEntity.ok(
                companyRepository
                        .findByEmail(loginDto.getEmail())
                        .map(jwtService::encodeCompany)
                        .orElse("")
        );
    }

    @GetMapping("/me")
    public String getPrincipal(Principal principal) {
        return "<h1> Company: " + principal.getName() + " </h1>";
    }
}
