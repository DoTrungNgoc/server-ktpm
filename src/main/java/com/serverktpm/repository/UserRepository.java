package com.serverktpm.repository;

import com.serverktpm.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByIdAndIsBlock(String id, boolean isBlock);

}
