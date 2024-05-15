package com.capstone.CapstoneServer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    @NotBlank(message = "Please provide valid username")
    private String userName;

    @Column(unique = true)
    @NotBlank(message = "Please provide valid mail ")
    private String email;

    private Date dateOfCreation;

    @NotBlank(message = "Please provide password")
    private String password;


}