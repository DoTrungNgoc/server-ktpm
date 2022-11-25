package com.ktpm.authservice.service;

import com.ktpm.authservice.model.User;
import com.ktpm.authservice.repository.ImageRepository;
import com.ktpm.authservice.repository.UserRepository;
import com.ktpm.authservice.response.model.UserDetailsIm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepo;
    private final ImageService imageService;
    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepo.findByIdAndIsBlock(userId, false);
        if (user != null) {
            return new UserDetailsIm(user);
        }
        throw new UsernameNotFoundException("Invalid username or password.");
    }
}