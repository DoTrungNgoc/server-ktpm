package com.serverktpm.request.auth;


import lombok.Data;

@Data
public class RegisterRequest {
    private String phoneNumber;
    private String password;
}
