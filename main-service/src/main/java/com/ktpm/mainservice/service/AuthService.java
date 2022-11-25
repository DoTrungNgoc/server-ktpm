package com.ktpm.mainservice.service;


import com.ktpm.mainservice.exception.NotFoundException;
import com.ktpm.mainservice.exception.ServiceException;
import com.ktpm.mainservice.model.User;
import com.ktpm.mainservice.repository.UserRepository;
import com.ktpm.mainservice.request.auth.LoginRequest;
import com.ktpm.mainservice.request.auth.RegisterRequest;
import com.ktpm.mainservice.response.auth.LoginResponse;
import com.ktpm.mainservice.response.auth.RegisterResponse;
import com.ktpm.mainservice.util.JwtTokenProvider;
import com.ktpm.mainservice.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



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

}
