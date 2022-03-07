package com.test.bookstore.repositories;

import java.util.Optional;

import com.test.bookstore.models.LikeModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<LikeModel, Integer> {
    public Optional<LikeModel> findByBookId(Long bookId);
    public void deleteByBookId(Long bookId);
}
