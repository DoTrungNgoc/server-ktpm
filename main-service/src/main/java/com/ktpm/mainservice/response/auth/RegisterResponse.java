package com.ktpm.mainservice.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String userId;
    private String accessToken;
}
