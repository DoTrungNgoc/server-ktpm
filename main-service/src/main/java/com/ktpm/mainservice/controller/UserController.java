package com.ktpm.mainservice.controller;


import com.ktpm.mainservice.request.auth.model.ChangePasswordRequest;
import com.ktpm.mainservice.request.auth.model.UserCreateRequest;
import com.ktpm.mainservice.request.auth.model.UserUpdateRequest;
import com.ktpm.mainservice.response.WrapResponse;
import com.ktpm.mainservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("main/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;


    @PostMapping("/create")
    public WrapResponse create(@RequestBody UserCreateRequest request) {
        return WrapResponse.ok(userService.createUser(request));
    }

    @PutMapping("/update")
    public WrapResponse update( @RequestBody UserUpdateRequest request){
        return WrapResponse.ok(userService.updateUserById(request));
    }
    @GetMapping("phone-number/{phoneNumber}")
    public WrapResponse getUserByPhoneNumber(@PathVariable String phoneNumber){
        return WrapResponse.ok(userService.getUserByPhoneNumber(phoneNumber));
    }
    @PostMapping("/change-password")
    public WrapResponse changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return WrapResponse.ok(userService.changePassword(request));
    }





}
