package com.ktpm.mainservice.controller;

import com.ktpm.mainservice.request.auth.model.ConversationCreateRequest;
import com.ktpm.mainservice.response.WrapResponse;
import com.ktpm.mainservice.service.ConversationService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("main/conversation")
public class ConversationController {
    private final ConversationService conversationService;


    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/create")
    public WrapResponse create(@RequestBody ConversationCreateRequest request){
        return WrapResponse.ok(conversationService.createConversation(request));
    }

    @GetMapping("/all-of-user")
    public WrapResponse getConversationOfUser() {
        return  WrapResponse.ok(conversationService.getAllConversationOfUser());
    }


}