package com.test.bookstore.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "likes")
public @Builder class LikeModel {
    @Id
    @NonNull
    public Long bookId;
    public Integer likes;
    @Singular public List<String> customers;
}
