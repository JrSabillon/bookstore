package com.test.bookstore.models;

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
    public Long bookId;

    public String title;
    public String description;
    public Integer stock;
    public String salePrice;

    @Builder.Default
    public Boolean available = true;

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

    public String getsalePrice(){
        if(salePrice == null)
            throw new NullPointerException();

        return salePrice;
    } 
}
