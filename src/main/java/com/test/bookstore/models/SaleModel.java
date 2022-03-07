package com.test.bookstore.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sales")
public class SaleModel {
    @Id
    public String id;
    @NonNull
    public Long bookId;
    @NonNull    
    public String customerEmail;
    public BigDecimal price;
    @CreatedDate
    public LocalDate saleDate;
}
