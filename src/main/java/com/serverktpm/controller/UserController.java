package com.serverktpm.controller;

import com.serverktpm.request.auth.model.UserCreateRequest;
import com.serverktpm.request.auth.model.UserUpdateRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
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
}
