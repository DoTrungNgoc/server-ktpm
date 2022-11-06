package com.serverktpm.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.serverktpm.model.User;
import com.serverktpm.repository.ImageRepository;
import com.serverktpm.response.auth.LoginResponse;
import com.serverktpm.response.model.UserResponse;

@Service
public class ImageService {
    private final ImageRepository imageRepo;

    public ImageService(ImageRepository imageRepo) {
        this.imageRepo = imageRepo;
    }

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
