package com.ktpm.mainservice.repository;

import com.ktpm.mainservice.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends MongoRepository<Image,String> {
}
