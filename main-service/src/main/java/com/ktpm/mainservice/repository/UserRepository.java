package com.ktpm.mainservice.repository;

import com.ktpm.mainservice.model.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Cacheable("user")
    Optional<User> findByPhoneNumberAndIsBlock(String phoneNumber, boolean isBlock);

    @Cacheable("user")
    User findByIdAndIsBlock(String id, boolean isBlock);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @CachePut(value = "user")
    User save(User user);
}
