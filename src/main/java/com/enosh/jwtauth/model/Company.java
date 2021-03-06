package com.enosh.jwtauth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends UserEntity {

    private String name;

    public Company(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
