package com.enosh.jwtauth;

import com.enosh.jwtauth.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtauthApplicationTests {

    @Autowired
    private JwtService service;

    @Test
    void contextLoads() {
    }

    @Test
    void printSecret(){
        service.printSecret();
    }

}
