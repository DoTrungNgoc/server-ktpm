package com.ktpm.mainservice.response.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String avatar;
    private String coverImage;
    private Date dateOfBirth;
}
