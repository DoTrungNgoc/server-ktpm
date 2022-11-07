package com.serverktpm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.serverktpm.model.User;
import com.serverktpm.repository.ImageRepository;
import com.serverktpm.response.auth.LoginResponse;
import com.serverktpm.response.model.UserResponse;

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
