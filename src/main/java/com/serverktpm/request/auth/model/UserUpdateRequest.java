package com.serverktpm.request.auth.model;

import lombok.Data;

import java.util.Date;
@Data
public class UserUpdateRequest {
    private String name;
    private String id;
    private String avatar;
    private String coverImage;
    private Date dateOfBirth ;
}
