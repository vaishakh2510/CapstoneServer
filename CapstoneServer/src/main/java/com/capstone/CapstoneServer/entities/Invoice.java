package com.capstone.CapstoneServer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "invoice_id")  // Specify column name for invoiceId
    private int invoiceId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")  // Specify column names for user relationship
    private User user;

    @NotBlank(message = "Please provide client name")
    @Column(name = "client_name")  // Specify column name for clientName
    private String clientName;

    @Min(value = 3000, message = "Amount should be greater than 3000")
    @Column(name = "amount")  // Specify column name for amount
    private double amount;

    @Column(name = "invoice_date")  // Specify column name for date
    private Date date;

    @Column(name = "description")  // Specify column name for description
    private String description;
}
