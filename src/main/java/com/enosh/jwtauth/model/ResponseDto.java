package com.enosh.jwtauth.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T content;

}
