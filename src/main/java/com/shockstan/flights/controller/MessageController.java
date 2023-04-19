package com.shockstan.flights.controller;

import com.shockstan.flights.entity.messages;
import com.shockstan.flights.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/contact")
public class MessageController {
    @Autowired
    private MessageService msgService;
    @GetMapping
    public ResponseEntity getAllMsgs(){
        return new ResponseEntity(msgService.allMessages(), HttpStatus.OK);
    }
    @GetMapping("/name/{username}")
    public Optional<messages> getMsgByName(@PathVariable String username){
        return msgService.msgByUsername(username);
    }
    @GetMapping("/email/{email}")
    public Optional<messages> getMsgByEmail(@PathVariable String email){
        return msgService.msgByEmail(email);
    }
    @PostMapping()
    public messages contact(@RequestBody messages msg){
        return msgService.contact(msg);
    }
}
