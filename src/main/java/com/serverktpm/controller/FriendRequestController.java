package com.serverktpm.controller;

import com.serverktpm.request.auth.model.UpdateStatusFriendRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.FriendRequestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friend-request")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send-to-user/{toUserId}")
    public WrapResponse create(@PathVariable("toUserId") String toUserId) {
        return  WrapResponse.ok(friendRequestService.createFriendRequest(toUserId));
    }

    @PostMapping("/update-status")
    public WrapResponse updateStatus(@RequestBody UpdateStatusFriendRequest request) {
        return WrapResponse.ok(friendRequestService.updateStatusFriendRequest(request));
    }
}
