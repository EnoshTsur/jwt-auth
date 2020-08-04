package com.enosh.jwtauth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${secret}")
    private String SECRET_KEY;

    public void printSecret(){
        System.out.println(SECRET_KEY);
    }
}
