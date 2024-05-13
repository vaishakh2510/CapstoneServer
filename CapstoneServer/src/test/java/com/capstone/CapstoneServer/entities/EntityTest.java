package com.capstone.CapstoneServer.entities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {
    @Test
    public void testInvoiceAllFeilds() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setUserId(10);
        invoice.setClientName("Test Client");
        invoice.setAmount(100.00);
        invoice.setDate(Date.valueOf("2024-05-13"));
        invoice.setDescription("Test Invoice");

        // Use a single assertion object for better readability
        Assertions.assertThat(invoice)
                .hasFieldOrPropertyWithValue("invoiceId", 1)
                .hasFieldOrPropertyWithValue("userId", 10)
                .hasFieldOrPropertyWithValue("clientName", "Test Client")
                .hasFieldOrPropertyWithValue("amount", 100.00)
                .hasFieldOrPropertyWithValue("date", Date.valueOf("2024-05-13"))
                .hasFieldOrPropertyWithValue("description", "Test Invoice");
    }
    @Test
    public void testUserAllFields() {
        User user = new User();

        user.setUserId(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setDateOfCreation(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        user.setPassword("password123");

        // Use a single assertion object for better readability
        Assertions.assertThat(user)
                .hasFieldOrPropertyWithValue("userId", 1)
                .hasFieldOrPropertyWithValue("username", "testuser")
                .hasFieldOrPropertyWithValue("email", "test@example.com")
                .hasFieldOrPropertyWithValue("dateOfCreation", user.getDateOfCreation()) // Avoid hardcoding the date
                .hasFieldOrPropertyWithValue("password", "password123");
    }

}
