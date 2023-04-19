package com.shockstan.flights.service;

import com.shockstan.flights.entity.person;
import com.shockstan.flights.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<person> allPassengers(){
        log.info("All passenger data requested - "+Calendar.getInstance().getTime());
        return personRepository.findAll();
    }
    public person addPerson(person customer){
        log.info("Passenger "+customer.toString()+" added - "+Calendar.getInstance().getTime());
        return personRepository.save(customer);
    }
    public void clearPassengers(){
        personRepository.deleteAll();
    }
}
