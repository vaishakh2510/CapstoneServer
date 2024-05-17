package com.capstone.CapstoneServer.service;


import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.services.InvoiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceServiceImpl;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Test
    public void testGetInvoicesByUserId_Found() {
        int userId = 1;
        List<Invoice> expectedInvoices = Arrays.asList(new Invoice(), new Invoice());

        // Mock repository behavior
        Mockito.when(invoiceRepository.findByUserUserId(userId)).thenReturn(expectedInvoices);

        // Call the service method
        ResponseEntity<List<Invoice>> response = invoiceServiceImpl.getInvoicesByUserId(userId);

        // Assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedInvoices);
    }

    @Test
    public void testGetInvoicesByUserId_NotFound() {
        int userId = 2; // Assuming no invoices for this user

        // Mock repository behavior (empty list)
        Mockito.when(invoiceRepository.findByUserUserId(userId)).thenReturn(Collections.emptyList());

        // Call the service method
        ResponseEntity<List<Invoice>> response = invoiceServiceImpl.getInvoicesByUserId(userId);

        // Assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull(); // Or verify it's an empty list
    }
}
