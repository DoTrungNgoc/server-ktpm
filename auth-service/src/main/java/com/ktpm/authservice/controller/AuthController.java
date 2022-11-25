package com.ktpm.authservice.controller;
import com.ktpm.authservice.response.WrapResponse;
import com.ktpm.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ktpm.authservice.request.LoginRequest;
import com.ktpm.authservice.request.RegisterRequest;


@RestController
@RequestMapping("/auth")
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
