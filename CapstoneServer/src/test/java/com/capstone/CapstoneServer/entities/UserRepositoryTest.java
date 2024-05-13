package com.capstone.CapstoneServer.entities;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Sample username and user object
        String username = "test_user";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setEmail("test@example.com"); // Set other user properties as needed
        expectedUser.setDateOfCreation(new java.util.Date()); // Set date of creation

         userRepository.save(expectedUser);

        // Call the findByUsername method
        User actualUser = userRepository.findByUsername(username);

        // Assertions
        assertNotNull(actualUser); // Check if user is found
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());

    }
}
