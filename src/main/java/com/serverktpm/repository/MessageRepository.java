package com.serverktpm.repository;

import com.serverktpm.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends MongoRepository<Message,String> {
    Page<Message> findMessagesByConversationIdOrderByTimeSendDesc(String conversationId, Pageable page);
    long deleteByConversationIdAndSenderId(String conversationId, String senderId);

    List<Message> findAllByConversationIdAndType(String conversationId, int type);
}
