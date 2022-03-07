package com.test.bookstore.controllers;

import java.util.Map;

import com.test.bookstore.models.TransactionModel;
import com.test.bookstore.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    
    /* @GetMapping("/books/{bookId}")
    public ResponseEntity<Map<String, Object>> getTransactions(@PathVariable("bookId") Long bookId, 
    @RequestParam(name = "from", defaultValue = "") String from, @RequestParam(name = "to", defaultValue = "") String to){
        Map<String, Object> transactions = transactionService.getTransactions(bookId, from, to);
        
        if(transactions == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    } */

    @GetMapping("/books/{bookId}")
    public ResponseEntity<TransactionModel> getTransactions(@PathVariable("bookId") Long bookId, 
    @RequestParam(name = "from", defaultValue = "") String from, @RequestParam(name = "to", defaultValue = "") String to){
        TransactionModel transactions = transactionService.getTransactions(bookId, from, to);
        
        if(transactions == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
