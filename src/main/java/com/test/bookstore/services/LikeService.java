package com.test.bookstore.services;

import java.util.Optional;

import com.test.bookstore.models.BookModel;
import com.test.bookstore.models.LikeModel;
import com.test.bookstore.models.POJO.LikePojo;
import com.test.bookstore.repositories.LikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired 
    LikeRepository likeRepository;
    
    @Autowired
    BookService bookService;

    public LikeModel saveLike(LikePojo like) {
        Optional<BookModel> book = bookService.findBookById(like.bookId); 

        if(book.isPresent()){
            BookModel bookData = book.get();

            if(bookData.getAvailable()){
                //Solo los libros disponibles pueden recibir like
                Optional<LikeModel> currentLikes = likeRepository.findByBookId(like.bookId);
                LikeModel likeData;

                //Revisar si ya existen likes registrados en la BD
                if(currentLikes.isPresent()){
                    likeData = currentLikes.get();
                    //Si existen likes y no le ha dado like previamente agregar el cliente que ha registrado su like
                    if(!likeData.customers.contains(like.customerEmail)){
                        likeData.customers.add(like.customerEmail); 
                        likeData.likes++;

                        return likeRepository.save(likeData);
                    } 

                    return likeData; //Si ya le dio like enviar una respuesta sin actualizar BD
                }else{
                    //Si no existen likes aun, crear un nuevo registro con el like actual
                    likeData = LikeModel.builder()
                    .bookId(like.bookId)
                    .likes(1)
                    .customer(like.customerEmail)
                    .build();
                    
                    return likeRepository.save(likeData);
                }
            }
        }

        return null; //No hay datos entonces enviar null para mostrar NOT_FOUND
    }
    
}
