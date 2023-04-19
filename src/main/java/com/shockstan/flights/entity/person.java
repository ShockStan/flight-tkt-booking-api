package com.shockstan.flights.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "passenger")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class person {
    @Id
    private ObjectId Id;
    private String name;
    private int age;
    private String personalId;
    private String seatNo;
    private String tier;
    private String planeNo;
    private int price;
    private String flightcompany;
    private String tktId;
}
