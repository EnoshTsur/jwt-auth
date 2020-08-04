package com.enosh.jwtauth.company;

import com.enosh.jwtauth.model.Company;
import com.enosh.jwtauth.repository.CompanyRepository;
import com.enosh.jwtauth.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyTests {

    @Autowired
    private CompanyRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    void findByEmail(){
        repository.findByEmail("arom@mail")
                .ifPresent(System.out::println);
    }

    @Test
    void findAll() {
        System.out.println(
                repository.findAll()
        );
    }
}
