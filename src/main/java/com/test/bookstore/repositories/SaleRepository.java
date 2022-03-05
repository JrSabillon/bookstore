package com.test.bookstore.repositories;

import com.test.bookstore.models.SaleModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends MongoRepository<SaleModel, String>{
    
}
