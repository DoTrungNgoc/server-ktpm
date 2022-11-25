package com.ktpm.mainservice.exception;

public class SecurityException extends RuntimeException{
    private String message;

    public SecurityException(String message){
        super();
        this.message = message;
    }
}
