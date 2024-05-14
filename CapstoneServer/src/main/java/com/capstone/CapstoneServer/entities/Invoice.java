package com.capstone.CapstoneServer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
   // private int userId;
    private String clientName;
    private double amount;
    private Date date;
    private String description;


}