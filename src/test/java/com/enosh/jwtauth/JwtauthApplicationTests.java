package com.enosh.jwtauth;

import com.enosh.jwtauth.model.Company;
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
    void encode(){
        Company company = new Company(
                "aroma@cool",
                "cool-aroma",
                "123"
        );
        company.setId(1L);

        String jwt = service.encodeJwt(company);
        System.out.println(jwt);
    }

    @Test
    void decode(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTY1NTU4NzcsImp0aSI6IjEiLCJzdWIiOiJhcm9tYUBjb29sIn0.L8xVLcWQ2ffLr2sSCgWtVok7FLsI4_yeUXhLd63Mpug";
        System.out.println(service.decodeJwt(jwt));
    }

}
