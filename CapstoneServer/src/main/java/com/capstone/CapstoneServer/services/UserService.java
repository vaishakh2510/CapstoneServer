package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //used to get user id
    public ResponseEntity<Integer> getUser(String username, String password) {
        User user = userRepository.findByUsername(username);
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
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(2); // Username already exists
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(3); // Email already exists
        }
        userRepository.save(user);
        return ResponseEntity.ok(1); // Success
    }

}
