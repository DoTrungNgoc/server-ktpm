package com.serverktpm.repository;

import com.serverktpm.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    boolean existsByListMemberId(List<String> listMemberId);

    @Query(value = "{listMemberId:?0}",sort = "{createdDate:-1}")
    List<Conversation> findConversationsOfUser(String userId);
}
