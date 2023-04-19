package com.shockstan.flights.repository;

import com.shockstan.flights.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findUserByemail(String email);
}
