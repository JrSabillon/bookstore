package com.test.bookstore.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
    public Long bookId;
    @Singular public List<LocalDate> sales;
    public BigDecimal totalRevenue;
    @Singular public List<String> customers; 
}
