package com.capstone.CapstoneServer.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class InvoiceDto {

     private int invoiceId;
     private Date invoiceDate;

    @Min(value = 3000, message = "Amount should be greater than 3000")
    private double amount;

    @NotBlank(message = "Please provide client name")
    private String clientName;

    private String description;

    @NotBlank(message = "User ID is required")
    private int userId;

}
