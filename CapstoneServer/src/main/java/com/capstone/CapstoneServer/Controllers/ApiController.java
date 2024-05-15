package com.capstone.CapstoneServer.Controllers;

import com.capstone.CapstoneServer.datas.UserResponse;
import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import com.capstone.CapstoneServer.services.InvoiceService;
import com.capstone.CapstoneServer.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {

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
    public ResponseEntity<Integer> getUser(@RequestParam String userName, @RequestParam String password) {
        System.out.println("login called-----------" + userName);
        return userService.getUser(userName, password);
    }


    //adding invoice------------------------------------------------------------------
    @PostMapping("/invoice")
    public ResponseEntity<Void> addInvoice( @RequestBody Invoice invoice) {
        return invoiceService.addInvoice(invoice);
    }
    // getting invoice----------------------------------------------------------------
    @GetMapping("/invoice/{userId}")
    public ResponseEntity<List<Invoice>> getInvoicesByUserId(@PathVariable int userId) {
        System.out.println("invoice called ----userid" + userId);
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
