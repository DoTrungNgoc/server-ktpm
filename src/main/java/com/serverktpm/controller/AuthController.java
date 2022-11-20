package com.serverktpm.controller;
import com.serverktpm.request.auth.model.VerifyOtpRequest;
import com.serverktpm.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.serverktpm.request.auth.LoginRequest;
import com.serverktpm.request.auth.RegisterRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
	private final AuthService authService;
    private final OTPService otpService;

    @PostMapping("/register")
    public WrapResponse register(@RequestBody RegisterRequest request){
        return WrapResponse.ok(authService.register(request));
    }

    @PostMapping("/login")
    public WrapResponse login(@RequestBody LoginRequest request) {
        return WrapResponse.ok(authService.login(request));
    }

    @PostMapping("/send-otp-verify-phone-number/{phoneNumber}")
    public WrapResponse<Boolean> sendOtpVerifyPhone(@PathVariable String phoneNumber){
        return WrapResponse.ok(otpService.sendOTP(phoneNumber));
    }

    @PostMapping("/verify-otp-phone-number")
    public WrapResponse<Boolean> verifyOtpPhoneNumber(@RequestBody VerifyOtpRequest request){
        return WrapResponse.ok(otpService.validOtp(request.getPhoneNumber(),request.getOtp()));
    }

}
