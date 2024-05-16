package com.capstone.CapstoneServer.Controllers;

import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.model.InvoiceDto;
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


    @Autowired
    private InvoiceRepository invoiceRepository;

    //creating user------------------------------------------------------------------
    @PostMapping("/user")
    public ResponseEntity<Integer> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
    //getting user-------------------------------------------------------------------
    @GetMapping("/user")
    public ResponseEntity<Integer> getUser(@Valid @RequestParam String userName, @RequestParam String password) {
        log.info("getUser: userName=" + userName + ", password=" + password);
        return userService.getUser(userName, password);
    }


    //adding invoice------------------------------------------------------------------
    @PostMapping("/invoice")
    public ResponseEntity<Void> addInvoice(@Valid @RequestBody InvoiceDto invoice) {
        return invoiceService.addInvoice(invoice);
    }
    // getting invoice----------------------------------------------------------------
    @GetMapping("/invoice/{userId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByUserId(@PathVariable int userId) {
        log.info("Getting invoices by user id {}", userId);
        return invoiceService.getInvoicesByUserId(userId);
    }

    @PutMapping("/invoice/{id}")
    public ResponseEntity<Void> updateInvoice(@PathVariable("id") int id, @RequestBody Invoice invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }


    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") int id) {
        return invoiceService.deleteInvoice(id);
    }

}
