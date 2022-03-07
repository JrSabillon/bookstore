package com.test.bookstore.models;

import java.math.BigDecimal;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookModel {
    @Id
    private Long bookId;
    public String title;
    private String description;
    public Integer stock;
    public BigDecimal salePrice;

    @Builder.Default
    private Boolean available = true;

    /**
     * Se crearon estos getters manualmente para controlar los campos requeridos
     * NonNull de lombok me presenta algunos problemas por los constructores.
     */

    public String getTitle(){
        if(title == null)
            throw new NullPointerException();

        return title;
    } 

    public Integer getStock(){
        if(stock == null)
            throw new NullPointerException();

        return stock;
    }

    public BigDecimal getsalePrice(){
        if(salePrice == null)
            throw new NullPointerException();

        return salePrice;
    } 
}
