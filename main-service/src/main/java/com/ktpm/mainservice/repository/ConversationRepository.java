package com.ktpm.mainservice.repository;

import com.ktpm.mainservice.model.Conversation;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    boolean existsByListMemberId(List<String> listMemberId);

    @Cacheable(value = "conversation")
    @Query(value = "{listMemberId:?0}",sort = "{createdDate:-1}")
    List<Conversation> findConversationsOfUser(String userId);

    @CachePut(value = "conversation")
    Conversation save(Conversation conversation);
}
