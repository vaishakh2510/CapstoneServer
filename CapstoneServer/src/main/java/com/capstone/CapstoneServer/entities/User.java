package com.capstone.CapstoneServer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")  // Table name remains "app_user"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  // Specify column name for userId
    private int userId;

    @Column(unique = true, name = "username")  // Specify column names
    @NotBlank(message = "Please provide valid username")
    private String userName;

    @Column(unique = true, name = "email")  // Specify column names
    @NotBlank(message = "Please provide valid mail ")
    private String email;

    @CreationTimestamp
    @Column(name = "date_of_creation")  // Specify column name
    private Date dateOfCreation;

    @NotBlank(message = "Please provide password")
    @Column(name = "password")  // Specify column name
    private String password;
}
