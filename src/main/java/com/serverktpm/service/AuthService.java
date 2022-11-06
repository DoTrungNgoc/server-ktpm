package com.serverktpm.service;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.serverktpm.exception.NotFoundException;
import com.serverktpm.exception.ServiceException;
import com.serverktpm.model.User;
import com.serverktpm.repository.UserRepository;
import com.serverktpm.request.auth.LoginRequest;
import com.serverktpm.request.auth.RegisterRequest;
import com.serverktpm.response.auth.LoginResponse;
import com.serverktpm.response.auth.RegisterResponse;
import com.serverktpm.util.JwtTokenProvider;
import com.serverktpm.util.MapperUtil;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public AuthService(UserRepository userRepo, JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepo = userRepo;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepo.existsByPhoneNumber(request.getPhoneNumber())){
            throw new ServiceException("Phone number had register by another user");
        }
        User user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .name(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        String userId = userRepo.save(user).getId();
        return RegisterResponse.builder()
                .userId(user.getId())
                .accessToken(tokenProvider.generateToken(userId))
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepo.findByPhoneNumberAndIsBlock(request.getPhoneNumber(),false).orElseThrow(() -> new NotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new ServiceException("Password invalid");
        String jwt  = tokenProvider.generateToken(user.getId());
        LoginResponse response = MapperUtil.mapObject(user,LoginResponse.class);
        response.setAccessToken(jwt);
        return imageService.mapImageUserForLoginResponse(user,response);
    }

    public String getLoggedUserId() {
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        return userIdString;
    }

}
