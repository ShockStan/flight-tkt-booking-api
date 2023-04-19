package com.shockstan.flights.controller;

import com.shockstan.flights.entity.flight;
import com.shockstan.flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/flights")
@CrossOrigin
public class FlightController {
    @Autowired
    private FlightService flightService;
    @GetMapping
    public ResponseEntity<List<flight>> getAllFlights(){
        return new ResponseEntity<List<flight>>(flightService.allFlights(), HttpStatus.OK);
    }
    @GetMapping("/now")
    public ResponseEntity flightByTime(){
        return new ResponseEntity(flightService.flightByTime(), HttpStatus.OK);
    }
    @GetMapping("/flight/{flightNo}")
    public ResponseEntity<Optional<flight>> getoneflight(@PathVariable String flightNo){
        return new ResponseEntity<Optional<flight>>(flightService.findflightByflightNo(flightNo), HttpStatus.OK);
    }
    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<Optional<List<flight>>> getfromto(@PathVariable String from, @PathVariable String to){
        if(from.equalsIgnoreCase(to)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Optional<List<flight>>>(flightService.findflightByfromto(from,to) ,HttpStatus.OK);
    }
    @PatchMapping("/bookTkts/{flightNo}")
    public ResponseEntity<Optional<flight>> updateflightSeats(@PathVariable String flightNo, @RequestBody flight Plane){
        return new ResponseEntity<>(flightService.updateSeats(flightNo, Plane), HttpStatus.OK);
    }
    @GetMapping("/bookedTkts")
    public ResponseEntity<List<flight>> showBookedTkts(){
        return new ResponseEntity<>(flightService.showFlightswithTktsBooked(),HttpStatus.OK);
    }
    @DeleteMapping("/admin/clear")
    public void deleteOld(){
        flightService.clearPassengerData();
    }
}
