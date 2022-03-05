package com.test.bookstore.repositories;

import com.test.bookstore.models.BookModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<BookModel, Long>{
    Page<BookModel> findByTitleLikeIgnoreCaseAndAvailable(String title, Boolean available, Pageable page);
    Page<BookModel> findByTitleLikeIgnoreCase(String title, Pageable page);
}