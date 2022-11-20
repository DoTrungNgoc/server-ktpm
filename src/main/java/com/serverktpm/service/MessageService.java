package com.serverktpm.service;

import com.serverktpm.model.Message;
import com.serverktpm.repository.MessageRepository;
import com.serverktpm.request.auth.model.MessageGetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Page<Message> getMessageOfConversation(MessageGetRequest request){
        Pageable page = PageRequest.of(request.getPageNumber(),request.getPageSize());
        return messageRepo.findMessagesByConversationIdOrderByTimeSendDesc(request.getConversationId(),page);
    }

}
