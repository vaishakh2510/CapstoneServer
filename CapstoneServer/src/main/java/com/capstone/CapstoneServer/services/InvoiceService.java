package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import com.capstone.CapstoneServer.exception.InvoiceNotFoundException;
import com.capstone.CapstoneServer.exception.UserNotFoundException;
import com.capstone.CapstoneServer.dto.InvoiceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
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
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        User user = userOptional.get();

        // Create and save Invoice
        Invoice invoice = new Invoice(0, user, invoiceDto.getClientName(), invoiceDto.getAmount(),invoiceDto.getInvoiceDate(), invoiceDto.getDescription());
        log.info("Invoice added-------------------: " );
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
    public ResponseEntity<Void> updateInvoice(int id, InvoiceDto invoiceDto) {

        Optional<Invoice> existingInvoiceOptional = invoiceRepository.findById(id);

        if (existingInvoiceOptional.isPresent()) {
            Invoice existingInvoice = existingInvoiceOptional.get();

            // Update existingInvoice from InvoiceDto
            existingInvoice.setClientName(invoiceDto.getClientName());
            existingInvoice.setAmount(invoiceDto.getAmount());
            existingInvoice.setDate(invoiceDto.getInvoiceDate());
            existingInvoice.setDescription(invoiceDto.getDescription());

            invoiceRepository.save(existingInvoice);
            return ResponseEntity.ok().build();
        } else {
            throw new InvoiceNotFoundException(id);
        }
    }


    //to delete invoice
    public ResponseEntity<Integer> deleteInvoice(int id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            log.info("Invoice deleted:"+id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
        } else {
            //return ResponseEntity.notFound().build();
            throw new InvoiceNotFoundException(id);
        }
    }


    public ResponseEntity<InvoiceDto> getInvoiceByInvoiceId(int invoiceId) {

        log.info("---------------getInvoicesByInvoiceId " + invoiceId);
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);  // Corrected case for 'Invoice'
        log.info("kkkkkkkkkkkkkkk",optionalInvoice);
        if (optionalInvoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Invoice invoice = optionalInvoice.get();

        // Create InvoiceDto object
        InvoiceDto invoiceDto = InvoiceDto.builder()
                .invoiceId(invoice.getInvoiceId())
                .invoiceDate(invoice.getDate())
                .clientName(invoice.getClientName())
                .amount(invoice.getAmount())
                .description(invoice.getDescription())
                .build();

        return ResponseEntity.ok(invoiceDto);
    }

    }

