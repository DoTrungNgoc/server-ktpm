package com.serverktpm.repository;

import com.serverktpm.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumberAndIsBlock(String phoneNumber, boolean isBlock);

    User findByIdAndIsBlock(String id, boolean isBlock);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
