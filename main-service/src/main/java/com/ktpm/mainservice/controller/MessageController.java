package com.ktpm.mainservice.controller;


import com.ktpm.mainservice.request.auth.model.MessageGetRequest;
import com.ktpm.mainservice.response.WrapResponse;
import com.ktpm.mainservice.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("main/message")
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