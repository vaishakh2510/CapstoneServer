package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.dto.UserDto;
import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    //used to get user id
    public int getUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            if (user.getPassword().equals(password)) return user.getUserId();
            else return -2; //password doesnt match
        }
        else return -1;  //user not found
    }



    //creating user
    public int createUser( UserDto user) {

        log.info("create user----------------------called");

        if (userRepository.findByEmail(user.getEmail()) != null) {
            // Email already exists
            return 2;
        }
        if (userRepository.findByUserName(user.getUserName()) != null) {
            // Username already exists
            return 3;
        }
        User userNew = User.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        userRepository.save(userNew);
        return 1;   // Success

    }


}
