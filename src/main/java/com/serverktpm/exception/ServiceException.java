package com.serverktpm.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private String message;

    public ServiceException(String massage) {
        super();
        this.message = massage;
    }

}
