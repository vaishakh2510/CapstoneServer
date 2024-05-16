package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import com.capstone.CapstoneServer.exception.InvoiceNotFoundException;
import com.capstone.CapstoneServer.exception.UserNotFoundException;
import com.capstone.CapstoneServer.model.InvoiceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    //to add invoice
    public ResponseEntity<Void> addInvoice(@RequestBody InvoiceDto invoiceDto) {

        // Fetch user object before creating invoice
        int userId = invoiceDto.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        User user = userOptional.get();

        // Create and save Invoice
        Invoice invoice = new Invoice(0, user, invoiceDto.getClientName(), invoiceDto.getAmount(), new Date(), invoiceDto.getDescription());
        invoiceRepository.save(invoice);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    //to get invoice
    public ResponseEntity<List<InvoiceDto>> getInvoicesByUserId(int userId) {

        log.info("---------------getInvoicesByUserId "+userId);
        List<Invoice> invoices = invoiceRepository.findByUserUserId(userId);
        log.info("@invoice return check");
        if (invoices.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<InvoiceDto> invoiceDtos = new ArrayList<>();
            for (Invoice invoice : invoices) {
                InvoiceDto invoiceDto = InvoiceDto.builder()
                        .invoiceId(invoice.getInvoiceId())
                        .clientName(invoice.getClientName())
                        .amount(invoice.getAmount())
                        .invoiceDate(invoice.getDate())
                        .description(invoice.getDescription())
                        // Set user ID if applicable (assuming InvoiceDto has a userId field)
                        .userId(invoice.getUser() != null ? invoice.getUser().getUserId() : null)
                        .build();
                 // Add InvoiceDto to the list
                invoiceDtos.add(invoiceDto);
            }
            return ResponseEntity.ok(invoiceDtos);

        }
    }

    //to edit invoice
    public ResponseEntity<Void> updateInvoice(int id, Invoice invoice) {
        Optional<Invoice> existingInvoiceOptional = invoiceRepository.findById(id);
        if (existingInvoiceOptional.isPresent()) {
            Invoice existingInvoice = existingInvoiceOptional.get();
            existingInvoice.setClientName(invoice.getClientName());
            existingInvoice.setAmount(invoice.getAmount());
            existingInvoice.setDate(invoice.getDate());
            existingInvoice.setDescription(invoice.getDescription());
            invoiceRepository.save(existingInvoice);
            return ResponseEntity.ok().build();
        } else {
            //want to throw exception insted
            throw new InvoiceNotFoundException(id);
        }
    }

    //to delete invoice
    public ResponseEntity<Void> deleteInvoice(int id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            //return ResponseEntity.notFound().build();
            throw new InvoiceNotFoundException(id);
        }
    }



}