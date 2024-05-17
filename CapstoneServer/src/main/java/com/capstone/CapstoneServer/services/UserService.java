package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.dto.UserDto;
import com.capstone.CapstoneServer.exception.UserNotFoundException;

public interface UserService {

    int createUser(UserDto userDto);

    int getUser(String username, String password) throws UserNotFoundException;
}
