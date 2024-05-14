package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    //used to get user id
    public ResponseEntity<Integer> getUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok(user.getUserId());
            } else {
                // Password does not match
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(401); // Unauthorized
            }
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404); // Not Found
        }
    }
    public ResponseEntity<Integer> createUser(User user) {

        log.info("create user----------------------called");
        user.setDateOfCreation(new Date());
        if (userRepository.findByUserName(user.getUserName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(2); // Username already exists
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(3); // Email already exists
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(1); // Success
    }


}
