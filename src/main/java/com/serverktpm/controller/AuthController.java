package com.serverktpm.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serverktpm.request.auth.LoginRequest;
import com.serverktpm.request.auth.RegisterRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
	private final AuthService authService;


    @PostMapping("/register")
    public WrapResponse register(@RequestBody RegisterRequest request){
        return WrapResponse.ok(authService.register(request));
    }

    @PostMapping("/login")
    public WrapResponse login(@RequestBody LoginRequest request) {
        return WrapResponse.ok(authService.login(request));
    }

}
