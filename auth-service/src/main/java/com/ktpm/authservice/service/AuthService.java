package com.ktpm.authservice.service;

import com.ktpm.authservice.exception.NotFoundException;
import com.ktpm.authservice.exception.ServiceException;
import com.ktpm.authservice.model.User;
import com.ktpm.authservice.repository.UserRepository;
import com.ktpm.authservice.response.model.UserResponse;
import com.ktpm.authservice.util.JwtTokenProvider;
import com.ktpm.authservice.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ktpm.authservice.request.LoginRequest;
import com.ktpm.authservice.request.RegisterRequest;
import com.serverktpm.response.auth.LoginResponse;
import com.serverktpm.response.auth.RegisterResponse;

import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final UserRepository userRepo;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;


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

    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        Optional<User> userOpt = userRepo.findByPhoneNumber(phoneNumber);
        if (!userOpt.isPresent()) {
            throw new NotFoundException("Not found userphoneNumber: " + phoneNumber);
        }
        UserResponse response = MapperUtil.mapObject(userOpt.get(), UserResponse.class);
        return imageService.mapImageUserForUserResponse(userOpt.get(),response);
    }
}

