package com.shockstan.flights.service;

import com.shockstan.flights.entity.flight;
import com.shockstan.flights.entity.person;
import com.shockstan.flights.repository.FlightRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
@Slf4j
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<flight> allFlights(){
        log.info("All Flight details Requested - "+Calendar.getInstance().getTime());
        return flightRepository.findAll();
    }
    public Optional<List<flight>> flightByTime(){
        ZoneId zone = ZoneId.of("Asia/Calcutta");
        LocalTime bookTime = LocalTime.now(zone).plusMinutes(30);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        List<flight> all = allFlights();
        List<flight> result = new ArrayList<>();
        for(flight plane: all){
            LocalTime planeTime = LocalTime.parse(plane.getEtd(), dtf);
            if(bookTime.isBefore(planeTime) || bookTime.equals(planeTime)){
                result.add(plane);
            }
        }
        Comparator<flight> sortByETD = Comparator.comparing(flight::getEtd);
        Collections.sort(result,sortByETD);
        log.info("Flights data requested at "+bookTime.minusMinutes(30).format(dtf)+" - "+Calendar.getInstance().getTime());
        return Optional.ofNullable(result);
    }
    public Optional<flight> findflightByflightNo(String flightNo){
        log.info(flightNo+" data requested - "+Calendar.getInstance().getTime());
        return flightRepository.findByflightNo(flightNo);
    }
    public Optional<List<flight>> findflightByfromto(String from, String to){
        return flightRepository.findByfromto(from,to);
    }
    public Optional<flight> updateSeats(String flightNo, flight Plane){
        flight air = findflightByflightNo(flightNo).get();
        List<person> users = Plane.getPassengers();
        for(person people: users){
            people.setPlaneNo(flightNo);
            people.setFlightcompany(air.getFlightCompany());
            String tier;
            if(people.getTier().equalsIgnoreCase("Business")){
                people.setPrice(air.getBusinessTktPrice());
                tier = "B";
            }
            else{
                people.setPrice(air.getEconomyTktPrice());
                tier = "E";
            }
            people.setTktId(air.getFrom()+"-"+air.getTo()+"-"+air.getFlightCompany()+"-"+tier+"-"+people.getSeatNo());
            personService.addPerson(people);
        }
        List<person> exist = air.getPassengers();
        exist.addAll(users);
        air.setPassengers(exist);
        List<String> econ = air.getEconomySeatsBooked();
        List<String> bus = air.getBusinessSeatsBooked();
        int econNos = 0, busNos = 0;
        for(person i:users){
            if(i.getTier().equalsIgnoreCase("Business")){
                bus.add(i.getSeatNo());
                busNos+=1;
            }
            else{
                econ.add(i.getSeatNo());
                econNos+=1;
            }
        }
        air.setBusinessSeatsBooked(bus);
        air.setEconomySeatsBooked(econ);

        int oldTotalBooked = air.getTotalTktsBooked(),
            oldBusBooked = air.getBusinessTktsBooked(),
            oldEconBooked = air.getEconomyTktsBooked();

        air.setTotalTktsBooked(oldTotalBooked + busNos + econNos);
        air.setTotalTktsAvail(air.getTotalTktsAvail() - busNos - econNos);
        air.setBusinessTktsBooked(bus.size());
        air.setEconomyTktsBooked(econ.size());
        air.setBusinessTktsAvail(air.getBusinessTktsAvail() - bus.size() + oldBusBooked);
        air.setEconomyTktsAvail(air.getEconomyTktsAvail() - econ.size() + oldEconBooked);

        log.info("Tickets have been booked in "+flightNo+". Passengers Info: "+air.getPassengers().toString()+" - "+Calendar.getInstance().getTime());

        return Optional.ofNullable(flightRepository.save(air));
    }
    @Scheduled(cron = "0 0 0 ? * 2", zone = "Asia/Calcutta")
    public void nightUpdate(){
        List<flight> all = showFlightswithTktsBooked();
        for(flight i:all){
            List<String> bookedTkts = new ArrayList<>();
            if(i.getBusinessTktsBooked()>0){
                i.setBusinessSeatsBooked(bookedTkts);
                i.setBusinessTktsAvail(i.getBusinessSeats());
                i.setBusinessTktsBooked(0);
            }
            if(i.getEconomyTktsBooked()>0){
                i.setEconomySeatsBooked(bookedTkts);
                i.setEconomyTktsAvail(i.getEconomySeats());
                i.setEconomyTktsBooked(0);
            }
            if(i.getTotalTktsBooked()>0) {
                i.setTotalTktsAvail(i.getTotalSeats());
                i.setTotalTktsBooked(0);
            }
            List<person> People = new ArrayList<>();
            i.setPassengers(People);
            flightRepository.save(i);
        }
        personService.clearPassengers();
        log.info("Reset carried out - Passenger Data cleared - "+Calendar.getInstance().getTime());
    }
    public List<flight> showFlightswithTktsBooked(){
        List<flight> all = allFlights();
        List<flight> withTkts = new ArrayList<>();
        for(flight i:all){
            if(i.getTotalTktsBooked()>0){
                withTkts.add(i);
            }
        }
        log.info("Flight with Tickets booked Requested - "+Calendar.getInstance().getTime());
        return withTkts;
    }

    public void clearPassengerData(){
        nightUpdate();
    }
}
