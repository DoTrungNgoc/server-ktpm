package com.ktpm.authservice.service;

import com.ktpm.authservice.model.User;
import com.ktpm.authservice.repository.ImageRepository;
import com.ktpm.authservice.response.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.serverktpm.response.auth.LoginResponse;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {
    private final ImageRepository imageRepo;

    public UserResponse mapImageUserForUserResponse(User user, UserResponse response){
        response.setAvatar(StringUtils.hasText(user.getAvatar()) ? imageRepo.findById(user.getAvatar()).get().getBase64(): "");
        response.setCoverImage(StringUtils.hasText(user.getCoverImage()) ? imageRepo.findById(user.getCoverImage()).get().getBase64(): "");
        return response;
    }

    public LoginResponse mapImageUserForLoginResponse(User user, LoginResponse response){
        response.setAvatar(StringUtils.hasText(user.getAvatar()) ? imageRepo.findById(user.getAvatar()).get().getBase64(): "");
        response.setCoverImage(StringUtils.hasText(user.getCoverImage()) ? imageRepo.findById(user.getCoverImage()).get().getBase64(): "");
        return response;
    }
}