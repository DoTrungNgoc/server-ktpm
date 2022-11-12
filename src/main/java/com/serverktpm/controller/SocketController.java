package com.serverktpm.controller;

import com.serverktpm.service.SocketService;
import com.serverktpm.socketModel.MessageSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocketController {
    private  final SocketService socketService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageSocket message) {
        socketService.handelAndSendMessageToAllUserOfConversation(message);
    }

}
