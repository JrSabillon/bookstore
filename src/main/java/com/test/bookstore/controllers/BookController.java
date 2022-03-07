package com.test.bookstore.controllers;

import java.util.Map;
import java.util.Optional;

import com.test.bookstore.models.BookModel;
import com.test.bookstore.models.POJO.BookPojo;
import com.test.bookstore.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookModel sabeBook(@RequestBody BookModel book){
        BookModel result = bookService.saveBook(book);
        return result;
    } 

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<BookModel> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookModel book){
        //Buscar libro en el repositorio
        Optional<BookModel> data = bookService.findBookById(bookId);

        //Verificar que el libro existe
        if(data.isPresent()){
            BookModel bookData = data.get();
            bookData.setTitle(book.getTitle());
            bookData.setDescription(book.getDescription());
            bookData.setStock(book.getStock());
            bookData.setSalePrice(book.getsalePrice());
            bookData.setAvailable(book.getAvailable());

            //Guardar datos del nuevo libro y devolverlo como respuesta
            return new ResponseEntity<>(
                bookService.updateBook(bookData), HttpStatus.OK
            );
        }   
        
        //Enviar una respuesta de no encontrado
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(path = "/{bookId}")
    public ResponseEntity<BookModel> patchBook(@PathVariable("bookId") Long bookId, @RequestBody BookPojo bookFields){
        Optional<BookModel> data = bookService.findBookById(bookId);
    
        if(data.isPresent()){
            //TODO: Mejorar este metodo para que sea dinamico, en caso de que el modelo cambie con el tiempo.
            if(bookFields.getTitle() != null) data.get().setTitle(bookFields.getTitle());
            if(bookFields.getStock() != null) data.get().setStock(bookFields.getStock());
            if(bookFields.getSalePrice() != null) data.get().setSalePrice(bookFields.getSalePrice());
            if(bookFields.getDescription() != null) data.get().setDescription(bookFields.getDescription());
            if(bookFields.getAvailable() != null) data.get().setAvailable(bookFields.getAvailable());

            return new ResponseEntity<>(bookService.updateBook(data.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @GetMapping()
    public Map<String, Object> getBooksPagedList(
        @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
        @RequestParam(name = "size", defaultValue = "12") Integer pageSize,
        @RequestParam(name = "sort", defaultValue = "title,asc") String sortBy,
        @RequestParam(name = "unavailable", defaultValue = "false") Boolean unavailable,
        @RequestParam(name = "title", defaultValue = "") String title){

        //la paginación comienza internamente desde la pagina 0 entonces restamos 1 a la pagina actual
        //si la página actual es 0 entonces la deja en 0.
        return bookService.getBooksPagedList(pageNumber <= 0 ? 0 : pageNumber - 1, pageSize, sortBy, unavailable, title);
    }
}
