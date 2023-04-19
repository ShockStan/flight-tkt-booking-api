package com.shockstan.flights.repository;


import com.shockstan.flights.entity.flight;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface FlightRepository extends MongoRepository<flight, ObjectId>{
   Optional<flight> findByflightNo(String flightNo);
   @Query("{from: ?0, to: ?1}")
   Optional<List<flight>> findByfromto(String from, String to);

}
