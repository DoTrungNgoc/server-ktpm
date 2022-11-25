package com.ktpm.authservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ImageRepository extends MongoRepository<ImageRepository,String> {
}
