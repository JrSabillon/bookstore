package com.test.bookstore.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.test.bookstore.models.SaleModel;
import com.test.bookstore.models.TransactionModel;
import com.test.bookstore.repositories.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    SaleRepository saleRepository;

    /* public Map<String, Object> getTransactions(Long bookId, String from, String to){
        Map<String, Object> transactions = new HashMap<String, Object>();

        List<SaleModel> sales = saleRepository.findBySaleDateBetweenAndBookId(LocalDate.parse(from), LocalDate.parse(to), bookId);
        
        if(sales.size() == 0)
        return null;
        
        List<LocalDate> saleDates = new ArrayList<LocalDate>();
        List<String> customers = new ArrayList<String>();
        Double totalRevenue = sales.stream().mapToDouble(sale -> sale.price).sum();

        sales.forEach(sale -> {
            saleDates.add(sale.getSaleDate());
            customers.add(sale.getCustomerEmail());
        });

        transactions.put("bookId", bookId);
        transactions.put("totalRevenue", totalRevenue);
        transactions.put("sales", saleDates);
        transactions.put("customers", customers);

        return transactions;
    } */

    public TransactionModel getTransactions(Long bookId, String from, String to){
        TransactionModel transactions = new TransactionModel();

        List<SaleModel> sales = saleRepository.findBySaleDateBetweenAndBookId(LocalDate.parse(from), LocalDate.parse(to), bookId);
        
        if(sales.size() == 0)
        return null;
        
        transactions = TransactionModel.builder()
        .bookId(bookId)
        .totalRevenue(sales.stream().map(sale -> sale.price).reduce(BigDecimal.ZERO, BigDecimal::add))
        .sales(sales.stream().map(sale -> sale.saleDate).collect(Collectors.toList()))
        .customers(sales.stream().map(sale -> sale.customerEmail).collect(Collectors.toList()))
        .build();
        
        return transactions;
    }
}
