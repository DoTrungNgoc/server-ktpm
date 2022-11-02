package com.serverktpm.exception;

public class SecurityException extends RuntimeException{
    private String message;

    public SecurityException(String message){
        super();
        this.message = message;
    }
}
