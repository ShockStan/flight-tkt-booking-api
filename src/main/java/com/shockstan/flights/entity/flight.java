package com.shockstan.flights.entity;

import java.util.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class flight {

    @Id
    private ObjectId _id;
    private String flightNo;
    private String from;
    private String to;
    private String etd;
    private String eta;
    private int totalSeats;
    private int economySeats;
    private int businessSeats;
    private int economyTktPrice;
    private int businessTktPrice;
    private String flightCompany;
    private int economyTktsAvail;
    private int economyTktsBooked;
    private int businessTktsAvail;
    private int businessTktsBooked;
    private int totalTktsAvail;
    private int totalTktsBooked;
    private List<String> businessSeatsBooked;
    private List<String> economySeatsBooked;
    private List<person> passengers;
}