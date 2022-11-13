package com.serverktpm.service;

import com.serverktpm.model.Conversation;
import com.serverktpm.model.Message;
import com.serverktpm.repository.ConversationRepository;
import com.serverktpm.repository.MessageRepository;
import com.serverktpm.socketModel.FriendRequestSocket;
import com.serverktpm.socketModel.MessageSocket;
import com.serverktpm.util.JwtTokenProvider;
import com.serverktpm.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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

    public void sendFriendRequestToUser(String toUserId, FriendRequestSocket friendRequest){
        messagingTemplate.convertAndSend("/user/" + toUserId + "/friend-request",friendRequest);
    }

}
