package com.shockstan.flights.service;

import com.shockstan.flights.entity.User;
import com.shockstan.flights.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> allUsers(){
        log.info("Login Initiated - "+Calendar.getInstance().getTime());
        return userRepository.findAll();
    }
    public Optional<User> oneUser(String email){
        if(userRepository.findUserByemail(email).isPresent()){
            User user = userRepository.findUserByemail(email).get();
            log.info(user.toString()+" - Logging in - "+Calendar.getInstance().getTime());
            return userRepository.findUserByemail(email);
        }
        return null;
    }
    public User addUser(User user){
        log.info(user.toString()+" - new User Signed up!! - "+Calendar.getInstance().getTime());
        return userRepository.save(user);
    }
}
