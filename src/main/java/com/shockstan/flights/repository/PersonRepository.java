package com.shockstan.flights.repository;

import com.shockstan.flights.entity.person;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<person, ObjectId> {
    Optional<person> findPassengersByplaneNo(String planeNo);
}
