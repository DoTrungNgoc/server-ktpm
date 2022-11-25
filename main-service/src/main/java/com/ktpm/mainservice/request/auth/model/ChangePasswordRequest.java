package com.ktpm.mainservice.request.auth.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordRequest {
    @Length(min = 8)
    private String newPassword;
    private String oldPassword;
}
