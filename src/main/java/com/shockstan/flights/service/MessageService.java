package com.shockstan.flights.service;

import com.shockstan.flights.entity.messages;
import com.shockstan.flights.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private MessageRepository msgRepo;
    public List<messages> allMessages(){
        log.info("ALl msgs requested - "+ Calendar.getInstance().getTime());
        return msgRepo.findAll();
    }
    public Optional<messages> msgByUsername(String username){
        log.info("Msgs by "+username+" requested - "+ Calendar.getInstance().getTime());
        return msgRepo.findByusername(username);
    }
    public Optional<messages> msgByEmail(String email){
        log.info("Msgs by "+email+" requested - "+ Calendar.getInstance().getTime());
        return msgRepo.findByemail(email);
    }
    public messages contact(messages msg){
        log.info(msg.toString() + " received - "+Calendar.getInstance().getTime());
        return msgRepo.save(msg);
    }
}
