package com.ktpm.authservice.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    private String message;

    public NotFoundException(String message){
        super();
        this.message = message;
    }

}
