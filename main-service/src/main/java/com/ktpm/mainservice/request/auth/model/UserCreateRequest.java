package com.ktpm.mainservice.request.auth.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserCreateRequest {
    private String name;
    private String phoneNumber;
    private String avatar;
    private String coverImage;
    private Date dateOfBirth ;
    private String password;
}
