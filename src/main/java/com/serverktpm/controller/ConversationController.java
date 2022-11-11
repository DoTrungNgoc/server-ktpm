package com.serverktpm.controller;


import com.serverktpm.request.auth.model.ConversationCreateRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.ConversationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
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