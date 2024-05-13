package com.capstone.CapstoneServer.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void testFindByUserId() {
        // Sample user ID and invoices
        int userId = 1;
        List<Invoice> expectedInvoices = new ArrayList<>();

        // Create sample Invoice objects
        Invoice invoice1 = new Invoice();
        invoice1.setUserId(userId);
        invoice1.setClientName("Client A");
        invoice1.setAmount(100.00);
        invoice1.setDate( Date.valueOf("2024-05-13"));
        invoice1.setDescription("Sample Invoice Description 1");

        Invoice invoice2 = new Invoice();
        invoice2.setUserId(userId);
        invoice2.setClientName("Client B");
        invoice2.setAmount(200.50);
        invoice2.setDate( Date.valueOf("2024-05-13")); // Use new Date() for current date
        invoice2.setDescription("Sample Invoice Description 2");

        // Add sample invoices to the list
        expectedInvoices.add(invoice1);
        expectedInvoices.add(invoice2);

        // Save invoices to the in-memory database
        invoiceRepository.saveAll(expectedInvoices);

        // Call the findByUserId method
        List<Invoice> actualInvoices = invoiceRepository.findByUserId(userId);

        // Assert that the returned invoices match the expected ones
        assertEquals(expectedInvoices, actualInvoices);
    }
}
