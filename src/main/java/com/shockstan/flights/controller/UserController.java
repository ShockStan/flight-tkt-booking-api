package com.shockstan.flights.controller;

import com.shockstan.flights.entity.User;
import com.shockstan.flights.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/config")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity getAllUsers(){
        return new ResponseEntity(userService.allUsers(), HttpStatus.OK);
    }
    @GetMapping("/login/{email}")
    public Optional<User> getUser(@PathVariable String email){
        return userService.oneUser(email);
    }
    @PostMapping("/signup")
    public User signUp(@RequestBody User user){
        user.setRole("User");
        return userService.addUser(user);
    }
}
