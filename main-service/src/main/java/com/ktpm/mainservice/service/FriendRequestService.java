package com.ktpm.mainservice.service;


import com.ktpm.mainservice.common.FriendRequestStatus;
import com.ktpm.mainservice.exception.NotFoundException;
import com.ktpm.mainservice.exception.ServiceException;
import com.ktpm.mainservice.model.Conversation;
import com.ktpm.mainservice.model.FriendRequest;
import com.ktpm.mainservice.model.User;
import com.ktpm.mainservice.repository.ConversationRepository;
import com.ktpm.mainservice.repository.FriendRequestRepository;
import com.ktpm.mainservice.repository.UserRepository;
import com.ktpm.mainservice.request.auth.model.UpdateStatusFriendRequest;
import com.ktpm.mainservice.response.model.UserResponse;
import com.ktpm.mainservice.socketModel.FriendRequestSocket;
import com.ktpm.mainservice.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepo;
    private final UserRepository userRepo;
    private final AuthService authService;
    private final ConversationRepository conversationRepo;
    private final SocketService socketService;
    private final ImageService imageService;

    public FriendRequestService(FriendRequestRepository friendRequestRepo, UserRepository userRepo, AuthService authService, ConversationRepository conversationRepo, SocketService socketService, ImageService imageService) {
        this.friendRequestRepo = friendRequestRepo;
        this.userRepo = userRepo;
        this.authService = authService;
        this.conversationRepo = conversationRepo;
        this.socketService = socketService;
        this.imageService = imageService;
    }

    public boolean createFriendRequest(String toUserId){
        String fromUserId = authService.getLoggedUserId();
        if (friendRequestRepo.existsByFromUserIdAndToUserId( fromUserId,toUserId)){
            throw new ServiceException("Friend request had existed");
        }
        if(!userRepo.findById(toUserId).isPresent()){
            throw new NotFoundException("Not found user id: " + toUserId);
        }
        FriendRequest friendRequest = FriendRequest.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId).build();
        friendRequest = friendRequestRepo.save(friendRequest);
        User fromUser = userRepo.findById(fromUserId).get();
        UserResponse fromUserResponse = MapperUtil.mapObject(fromUser, UserResponse.class);
        FriendRequestSocket friendRequestSocket = FriendRequestSocket.builder()
                .id(friendRequest.getId())
                .fromUser(imageService.mapImageUserForUserResponse(fromUser,fromUserResponse))
                .timeSend(friendRequest.getTimeSend())
                .build();
        socketService.sendFriendRequestToUser(toUserId,friendRequestSocket);
        return true;
    }

    public boolean updateStatusFriendRequest(UpdateStatusFriendRequest request){
        Optional<FriendRequest> opt = friendRequestRepo.findById(request.getId());
        if (!opt.isPresent()){
            throw new NotFoundException("Not found friend request id: " + request.getId());
        }
        FriendRequest friendRequest = opt.get();
        if (FriendRequestStatus.DENY.equals(request.getStatus())){
            friendRequest.setStatus(request.getStatus());
        }
        if (FriendRequestStatus.ACCEPT.equals(request.getStatus())){
            Conversation conversation = Conversation.builder()
                    .listMemberId(Arrays.asList(friendRequest.getToUserId(),friendRequest.getFromUserId())).build();
            User fromUser = userRepo.findById(friendRequest.getFromUserId()).get();
            User toUser = userRepo.findById(friendRequest.getToUserId()).get();
            fromUser.getPhoneBooks().add(toUser.getPhoneNumber());
            toUser.getPhoneBooks().add(fromUser.getPhoneNumber());
            userRepo.save(fromUser);
            userRepo.save(toUser);
            conversationRepo.save(conversation);
        }
        return true;
    }
}
