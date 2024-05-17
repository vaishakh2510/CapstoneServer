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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    //to add invoice
    public Boolean addInvoice(@RequestBody InvoiceDto invoiceDto) {

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
        return true;

    }



    //to get invoice by user
    public List<InvoiceDto> getInvoicesByUserId(int userId) {

        log.info("---------------getInvoicesByUserId "+userId);
        List<Invoice> invoices = invoiceRepository.findByUserUserId(userId);
        log.info("@invoice return check");
        if (invoices.isEmpty()) {
            throw new UserNotFoundException(userId);
        } else {
            List<InvoiceDto> invoiceDtos = new ArrayList<>();
            for (Invoice invoice : invoices) {
                InvoiceDto invoiceDto = InvoiceDto.builder()
                        .invoiceId(invoice.getInvoiceId())
                        .clientName(invoice.getClientName())
                        .amount(invoice.getAmount())
                        .invoiceDate(invoice.getDate())
                        .description(invoice.getDescription())
                        .userId(invoice.getUser() != null ? invoice.getUser().getUserId() : null)
                        .build();
                 // Add InvoiceDto to the list
                invoiceDtos.add(invoiceDto);
            }
             return invoiceDtos;

        }
    }

    //to edit invoice
    public Boolean updateInvoice(int id, InvoiceDto invoiceDto) {

        Optional<Invoice> existingInvoiceOptional = invoiceRepository.findById(id);
        if (existingInvoiceOptional.isPresent()) {
            Invoice existingInvoice = existingInvoiceOptional.get();

            // Update existingInvoice from InvoiceDto
            existingInvoice.setClientName(invoiceDto.getClientName());
            existingInvoice.setAmount(invoiceDto.getAmount());
            existingInvoice.setDate(invoiceDto.getInvoiceDate());
            existingInvoice.setDescription(invoiceDto.getDescription());

            invoiceRepository.save(existingInvoice);
            return true;
            //return ResponseEntity.ok().build();
        } else {
            throw new InvoiceNotFoundException(id);
        }
    }


    //to delete invoice
    public int deleteInvoice(int id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            log.info("Invoice deleted:"+id);
            return id;
        } else {
            throw new InvoiceNotFoundException(id);
        }
    }


    public InvoiceDto getInvoiceByInvoiceId(int invoiceId) {

        log.info("---------------getInvoicesByInvoiceId " + invoiceId);
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);
        log.info("kkkkkkkkkkkkkkk",optionalInvoice);
        if (optionalInvoice.isEmpty()) {
            throw new InvoiceNotFoundException(invoiceId);
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

        return invoiceDto;

    }

    }

