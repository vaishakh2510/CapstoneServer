package com.capstone.CapstoneServer.Controllers;

import com.capstone.CapstoneServer.datas.UserResponse;
import com.capstone.CapstoneServer.entities.Invoice;
import com.capstone.CapstoneServer.entities.InvoiceRepository;
import com.capstone.CapstoneServer.entities.User;
import com.capstone.CapstoneServer.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping("/user")
    public ResponseEntity<Integer> createUser(@RequestBody User user) {
    System.out.println(user);
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(2); // Username already exists
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(3); // Email already exists
        }
        userRepository.save(user);
        return ResponseEntity.ok(1); // Success
    }

    @GetMapping("/user")
    public ResponseEntity<Integer> getUser(@RequestParam String username, @RequestParam String password) {
        System.out.println("login called-----------"+username);
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok(user.getUserId());
            } else {
                // Password does not match
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(401); // Unauthorized
            }
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404); // Not Found
        }
    }



    @PostMapping("/invoice")
    public ResponseEntity<Void> addInvoice(@RequestBody Invoice invoice) {
        invoiceRepository.save(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/invoice/{userId}")
    public ResponseEntity<List<Invoice>> getInvoicesByUserId(@PathVariable int userId) {
        System.out.println("invoice called ----userid"+userId);
        List<Invoice> invoices = invoiceRepository.findByUserId(userId);
        if (invoices.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(invoices);
        }
    }


    @PutMapping("/invoice/{id}")
    public ResponseEntity<Void> updateInvoice(@PathVariable("id") int id, @RequestBody Invoice invoice) {
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
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/invoice/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") int id) {
        invoiceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
