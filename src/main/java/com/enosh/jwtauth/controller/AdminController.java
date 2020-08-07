package com.enosh.jwtauth.controller;

import com.enosh.jwtauth.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private JwtService jwtService;
}
