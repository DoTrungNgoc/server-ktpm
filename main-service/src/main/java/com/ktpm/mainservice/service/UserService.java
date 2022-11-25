package com.ktpm.mainservice.service;


import com.ktpm.mainservice.exception.NotFoundException;
import com.ktpm.mainservice.exception.ServiceException;
import com.ktpm.mainservice.model.Image;
import com.ktpm.mainservice.model.User;
import com.ktpm.mainservice.model.UserDetailsIm;
import com.ktpm.mainservice.repository.ImageRepository;
import com.ktpm.mainservice.repository.UserRepository;
import com.ktpm.mainservice.request.auth.model.ChangePasswordRequest;
import com.ktpm.mainservice.request.auth.model.UserCreateRequest;
import com.ktpm.mainservice.request.auth.model.UserUpdateRequest;
import com.ktpm.mainservice.response.model.UserResponse;
import com.ktpm.mainservice.util.MapperUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepo;
    private final ImageService imageService;
    private final AuthService authService;

    private RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8000/auth";

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepo.findByIdAndIsBlock(userId, false);
        if (user != null) {
            return new UserDetailsIm(user);
        }
        throw new UsernameNotFoundException("Invalid username or password.");
    }


    public Boolean checkUserExisted(String phoneNumber) {
        return userRepo.existsByPhoneNumber(phoneNumber);
    }


    //    Create user
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepo.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ServiceException("User has existed");
        }
        User user = MapperUtil.mapObject(request, User.class);
        user.setAvatar(imageRepo.save(Image.builder().base64(request.getAvatar()).build()).getId());
        user.setCoverImage(imageRepo.save(Image.builder().base64(request.getCoverImage()).build()).getId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepo.save(user);

        UserResponse response = MapperUtil.mapObject(user, UserResponse.class);
        response.setAvatar(request.getAvatar());
        response.setCoverImage(request.getCoverImage());
        return response;
    }

    public UserResponse updateUserById(UserUpdateRequest request) {
        Optional<User> optUser = userRepo.findById(request.getId());
        if (!optUser.isPresent()) {
            throw new ServiceException("User hasn't existed");
        }
        User user = optUser.get();

        user.setName(request.getName());
        user.setDateOfBirth(request.getDateOfBirth());
        if (StringUtils.hasText(user.getAvatar())) {
            Image image = imageRepo.findById(user.getAvatar()).get();
            image.setBase64(request.getAvatar());
            imageRepo.save(image);
        } else {
            Image image = new Image();
            image.setBase64(request.getAvatar());
            user.setAvatar(imageRepo.save(image).getId());
        }

        if (StringUtils.hasText((user.getCoverImage()))) {
            Image image = imageRepo.findById((user.getCoverImage())).get();
            image.setBase64((request.getCoverImage()));
            imageRepo.save(image);
        } else {
            Image image = new Image();
            image.setBase64((request.getCoverImage()));
            user.setCoverImage(imageRepo.save(image).getId());
        }
        user = userRepo.save(user);
        return MapperUtil.mapObject(user, UserResponse.class);
    }
    public boolean changePassword(ChangePasswordRequest request) {
        User user = userRepo.findById(authService.getLoggedUserId()).orElseThrow(() -> new NotFoundException("Not found user logged"));
        if (!passwordEncoder.matches(request.getOldPassword(),user.getPassword())) {
            throw new SecurityException("Old password not correct!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);
        return true;
    }

    @HystrixCommand(fallbackMethod = "defaultGetUserByPhoneNumber")
    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        ResponseEntity<UserResponse> response=  restTemplate.getForEntity(url + "/phone-number/" + phoneNumber,UserResponse.class);
        return response.getBody();
    }

    @SuppressWarnings("unused")
    UserResponse defaultGetUserByPhoneNumber(String productCode) {
        return UserResponse.builder().id("0000-0000-0000")
                .name("System not found").build();
    }
}
