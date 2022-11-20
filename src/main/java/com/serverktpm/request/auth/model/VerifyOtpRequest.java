package com.serverktpm.request.auth.model;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String phoneNumber;
    private int otp;
}
