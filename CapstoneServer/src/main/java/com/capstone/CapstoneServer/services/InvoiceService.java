package com.capstone.CapstoneServer.services;

import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.entities.UserRepository;
import com.capstone.CapstoneServer.exception.InvoiceNotFoundException;
import com.capstone.CapstoneServer.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public ResponseEntity<Void> addInvoice(Invoice invoice) {
        if (userRepository.existsById(invoice.getUser().getUserId())){
            invoiceRepository.save(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
        else throw new UserNotFoundException(invoice.getUser().getUserId());

    }

    //to get invoice
    public ResponseEntity<List<Invoice>> getInvoicesByUserId(int userId) {

        log.info("---------------getInvoicesByUserId "+userId);
        List<Invoice> invoices = invoiceRepository.findByUserUserId(userId);
        if (invoices.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(invoices);
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