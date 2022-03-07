package com.test.bookstore.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
