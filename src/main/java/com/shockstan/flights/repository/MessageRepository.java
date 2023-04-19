package com.shockstan.flights.repository;

import com.shockstan.flights.entity.messages;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface MessageRepository extends MongoRepository<messages, ObjectId> {
    Optional<messages> findByusername(String username);
    Optional<messages> findByemail(String email);

}
