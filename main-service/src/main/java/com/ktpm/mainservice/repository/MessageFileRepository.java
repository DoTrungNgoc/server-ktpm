package com.ktpm.mainservice.repository;

import com.ktpm.mainservice.model.MessageFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageFileRepository extends MongoRepository<MessageFile,String> {
}
