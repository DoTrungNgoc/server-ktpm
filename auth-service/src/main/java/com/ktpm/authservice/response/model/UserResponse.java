package com.ktpm.authservice.response.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String avatar;
    private String coverImage;
    private Date dateOfBirth;
}
