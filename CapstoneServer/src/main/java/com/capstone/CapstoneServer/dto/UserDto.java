package com.capstone.CapstoneServer.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Please provide username")
    private String userName;
    @NotBlank(message = "Please Provide email")
    private String email;
    @NotBlank(message = "Please provide password")
    private String password;
}
