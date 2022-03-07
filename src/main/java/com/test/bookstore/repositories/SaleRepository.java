package com.test.bookstore.repositories;

import java.time.LocalDate;
import java.util.List;

import com.test.bookstore.models.SaleModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends MongoRepository<SaleModel, String>{
    public List<SaleModel> findBySaleDateBetweenAndBookId(LocalDate from, LocalDate to, Long bookId);
    public void deleteByBookId(Long bookId);
}
