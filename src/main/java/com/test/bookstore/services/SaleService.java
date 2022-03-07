package com.test.bookstore.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import com.test.bookstore.models.BookModel;
import com.test.bookstore.models.SaleModel;
import com.test.bookstore.repositories.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    @Autowired
    SaleRepository saleRepository;

    @Autowired
    BookService bookService;

    public SaleModel saveSale(SaleModel sale) {
        Optional<BookModel> book = bookService.findBookById(sale.bookId);

        if(book.isPresent()){
            BookModel bookData = book.get();
            
            //Validamos que hay libros en stock o que no este disponible
            if(bookData.getStock() <= 0 || !bookData.getAvailable())
            return null;
            
            //registramos la venta
            sale.setPrice(bookData.getsalePrice());
            sale.setBookId(bookData.getBookId());
            sale.setSaleDate(LocalDate.now());
            SaleModel newSale = saleRepository.save(sale);

            //Actualizamos el stock
            bookData.setStock(bookData.getStock() - 1);
            //Si ya no hay stock pasar el libro a no disponible
            if(bookData.getStock() == 0)
            bookData.setAvailable(false);

            bookService.updateBook(bookData);

            return newSale;
        }


        return null;
    }
    
}
