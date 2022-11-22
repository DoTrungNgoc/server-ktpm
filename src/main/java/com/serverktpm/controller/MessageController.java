package com.serverktpm.controller;

import com.serverktpm.request.auth.model.MessageGetRequest;
import com.serverktpm.response.WrapResponse;
import com.serverktpm.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value= "upload-message-file")
    public WrapResponse uploadMessageFile(@RequestParam(name = "id") String id, @RequestPart(value = "file",name = "file") MultipartFile file) {
        return WrapResponse.ok(messageService.uploadMessageFile(id,file));
    }

}