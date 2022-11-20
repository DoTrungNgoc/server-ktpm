package com.serverktpm.repository;

import com.serverktpm.model.FriendRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends MongoRepository<FriendRequest,String> {
    boolean existsByFromUserIdAndToUserId(String fromUserId,String toUserId);
}
