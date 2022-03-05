package com.test.bookstore.controllers;

import com.test.bookstore.models.SaleModel;
import com.test.bookstore.services.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    SaleService saleService;
    
    @PostMapping()
    public ResponseEntity<SaleModel> saveSale(@RequestBody SaleModel sale){
        SaleModel savedSale = saleService.saveSale(sale);

        //No se ingreso la venta porque no hay stock disponible
        if(savedSale == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }
}
