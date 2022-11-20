package com.serverktpm.controller;

import com.serverktpm.request.auth.model.MessageGetRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/get-message-of-conversation")
    public WrapResponse getMessageOfConversation(@RequestBody MessageGetRequest request){
        return  WrapResponse.ok(messageService.getMessageOfConversation(request));
    }

}