package com.enosh.jwtauth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class UserEntity extends BaseEntity {

    protected String email;
}
