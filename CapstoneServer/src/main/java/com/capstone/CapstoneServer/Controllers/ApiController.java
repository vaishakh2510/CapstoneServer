package com.capstone.CapstoneServer.Controllers;

import com.capstone.CapstoneServer.dto.UserDto;
import com.capstone.CapstoneServer.dto.InvoiceDto;
import com.capstone.CapstoneServer.services.InvoiceService;
import com.capstone.CapstoneServer.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;



    @PostMapping("/user")
    public ResponseEntity<Integer> createUser(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }





    @GetMapping("/user")
    public ResponseEntity<Integer> getUser(@Valid @RequestParam String userName, @RequestParam String password) {
        log.info("getUser: userName=" + userName + ", password=" + password);
        return userService.getUser(userName, password);
    }





    @PostMapping("/invoice")
    public ResponseEntity<Void> addInvoice(@Valid @RequestBody InvoiceDto invoice) {
        return invoiceService.addInvoice(invoice);
    }





    @GetMapping("/invoice/{userId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByUserId(@PathVariable int userId) {
        log.info("Getting invoices by user id {}", userId);
        return invoiceService.getInvoicesByUserId(userId);
    }





    @GetMapping("/singleinvoice/{invocieId}")
    public ResponseEntity<InvoiceDto> getInvoicesByInvoiceId(@PathVariable int invocieId) {
        log.info("Getting invoices by invoice id {}", invocieId);
        return invoiceService.getInvoiceByInvoiceId(invocieId);
    }





    @PutMapping("/invoice/{id}")
    public ResponseEntity<Void> updateInvoice(@PathVariable("id") int id, @RequestBody InvoiceDto invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }






    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<Integer> deleteInvoice(@PathVariable("id") int id) {
        log.info("Deleting invoice with id {}", id);
        return invoiceService.deleteInvoice(id);
    }

}
