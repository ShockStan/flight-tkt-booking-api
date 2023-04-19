package com.shockstan.flights.controller;

import com.shockstan.flights.entity.flight;
import com.shockstan.flights.entity.person;
import com.shockstan.flights.service.FlightService;
import com.shockstan.flights.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private FlightService flightService;
    @GetMapping
    public ResponseEntity<List<person>> getAllPassengers(){
        return new ResponseEntity<List<person>>(personService.allPassengers() , HttpStatus.OK);
    }
}
