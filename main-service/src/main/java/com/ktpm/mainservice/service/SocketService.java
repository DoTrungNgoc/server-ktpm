package com.ktpm.mainservice.service;



import com.ktpm.mainservice.model.Conversation;
import com.ktpm.mainservice.model.Message;
import com.ktpm.mainservice.repository.ConversationRepository;
import com.ktpm.mainservice.repository.MessageRepository;
import com.ktpm.mainservice.socketModel.FriendRequestSocket;
import com.ktpm.mainservice.socketModel.MessageSocket;
import com.ktpm.mainservice.socketModel.ReactMessageSocket;
import com.ktpm.mainservice.socketModel.RevertMessageSocket;
import com.ktpm.mainservice.util.JwtTokenProvider;
import com.ktpm.mainservice.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ConversationRepository conversationRepo;
    private final JwtTokenProvider tokenProvider;
    private final MessageRepository messageRepo;

    public void handelAndSendMessageToAllUserOfConversation(MessageSocket message) {
        try {
            System.out.println(">>>>>>>> message");
            if (tokenProvider.validateToken(message.getAccessToken())) {
                String userId = tokenProvider.getUserIdFromJWT(message.getAccessToken());
                Conversation conversation = conversationRepo.findById(message.getConversationId()).get();
                System.err.println(userId);
                if (conversation.getListMemberId().contains(userId)) {
                    Message messageSave = MapperUtil.mapObject(message, Message.class);
                    messageSave.setSenderId(userId);
                    for (String memberId : conversation.getListMemberId()) {
                        messagingTemplate.convertAndSend("/user/" + memberId + "/chat", messageSave);
                    }
                    messageSave = messageRepo.save(messageSave);
                    conversation.setLastMessage(messageSave);
                    conversation.setLastSend(messageSave.getTimeSend());
                    conversationRepo.save(conversation);
                }
            } else {
                System.err.println(">>>>>>>> access token message not valid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handelRevertMessageAndSendToAllUserOfConversation(RevertMessageSocket revertMessageSocket) {
        try {
            System.out.println(">>>>>>>> revert message");
            if (tokenProvider.validateToken(revertMessageSocket.getAccessToken())) {
                String userId = tokenProvider.getUserIdFromJWT(revertMessageSocket.getAccessToken());
                messageRepo.findById(revertMessageSocket.getMessageId()).ifPresent(message -> {
                    if (message.getSenderId().equals(userId)) {
                        message.setContent(Arrays.asList("Tin nhắn đã được thu hồi"));
                        message = messageRepo.save(message);
                    }
                    Conversation conversation = conversationRepo.findById(message.getConversationId()).get();
                    for (String memberId : conversation.getListMemberId()) {
                        messagingTemplate.convertAndSend("/user/" + memberId + "/chat", message);
                    }
                });
            } else {
                System.err.println(">>>>>>>> access token message not valid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handelReactMessageAndSendToAllUserOfConversation(ReactMessageSocket reactMessageSocket) {
        try {
            System.out.println(">>>>>>>>> react message type: " + reactMessageSocket.getTypeReact());
            if (tokenProvider.validateToken(reactMessageSocket.getAccessToken())) {
                messageRepo.findById(reactMessageSocket.getMessageId()).ifPresent(message -> {
                    List<Integer> listReact = message.getReactList();
                    listReact.add(reactMessageSocket.getTypeReact());
                    message.setReactList(listReact);
                    message = messageRepo.save(message);
                    Conversation conversation = conversationRepo.findById(message.getConversationId()).get();
                    for (String memberId : conversation.getListMemberId()) {
                        messagingTemplate.convertAndSend("/user/" + memberId + "/chat", message);
                    }
                });
            } else {
                System.err.println(">>>>>>>> access token message not valid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFriendRequestToUser(String toUserId, FriendRequestSocket friendRequest){
        messagingTemplate.convertAndSend("/user/" + toUserId + "/friend-request",friendRequest);
    }

}
