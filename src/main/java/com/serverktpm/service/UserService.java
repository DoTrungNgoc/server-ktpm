package com.serverktpm.service;

import com.serverktpm.exception.ServiceException;
import com.serverktpm.model.Image;
import com.serverktpm.model.User;
import com.serverktpm.model.UserDetailsIm;
import com.serverktpm.repository.ImageRepository;
import com.serverktpm.repository.UserRepository;
import com.serverktpm.request.auth.model.UserCreateRequest;
import com.serverktpm.request.auth.model.UserUpdateRequest;
import com.serverktpm.response.model.UserResponse;
import com.serverktpm.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepo;

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

}
