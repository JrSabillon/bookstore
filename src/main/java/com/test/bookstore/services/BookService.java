package com.test.bookstore.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.test.bookstore.models.BookModel;
import com.test.bookstore.repositories.BookRepository;
import com.test.bookstore.repositories.LikeRepository;
import com.test.bookstore.repositories.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired 
    MongoOperations mongoOperations;

    public BookModel saveBook(BookModel book){
        if(book.title == null || book.stock == null || book.salePrice == null)
            throw new NullPointerException();
        
        book.setBookId(this.nextBookId());
        return bookRepository.save(book);
    }

    public BookModel updateBook(BookModel book){
        return bookRepository.save(book);
    }

    public ArrayList<BookModel> getBooks(){
        return (ArrayList<BookModel>)bookRepository.findAll();
    }

    public Optional<BookModel> findBookById(Long bookId){
        return bookRepository.findById(bookId);
    }

    public void deleteBookById(Long bookId){
        bookRepository.deleteById(bookId);
        likeRepository.deleteByBookId(bookId);
        saleRepository.deleteByBookId(bookId);
    }

    /**
     * Obtener los libros con paginación y filtros
     * @param pageNumber Página en la que se encuentra viendo libros
     * @param pageSize Tamaño de la pagina(cantidad de libros que se muestran por página)
     * @param sortBy Tipo de ordenamiento, se lee la url (Campo del json y asc o desc separado por una coma) ejem. title,asc
     * @param showUnavailables Booleano que indica si muestra o no los disponibles, false para no mostrarlos, true para mostrarlos
     * @param title Filtro de busqueda por titulo
     * @return los datos de los libros existentes con paginación incluida
     */
    public Map<String, Object> getBooksPagedList(Integer pageNumber, Integer pageSize, String sortBy, Boolean showUnavailables, String title) {
        //Verificar direccion del ordenamiento y campo a ordenar segun el parametro de ordenamiento.
        String sortField = sortBy.split(",")[0];
        Direction sortDirection = sortBy.split(",")[1].toLowerCase().equals("asc") ? Direction.ASC : Direction.DESC;

        //Creacion y llenado del JSON que se mostrara en respuesta
        Map<String, Object> pageModel = new HashMap<String, Object>();
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortField));
        Page<BookModel> bookPage;

        //Se crearon 2 metodos adicionales en el repositorio para buscar por titulo o por titulo y disponibles.
        //Si se envia `True` busca por titulo los libros aunque no esten disponibles
        //Si se envia `False` busca por titulo y solo los que esten disponibles.
        //Para mayor referencia buscar en: https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
        if(showUnavailables)
        bookPage = bookRepository.findByTitleLikeIgnoreCase(title, page);
        else
        bookPage = bookRepository.findByTitleLikeIgnoreCaseAndAvailable(title, true, page);

        pageModel.put("content", bookPage.getContent());
        pageModel.put("size", pageSize);
        pageModel.put("numberOfElements", bookPage.getNumberOfElements());
        pageModel.put("totalElements", bookPage.getTotalElements());
        pageModel.put("number", bookPage.getNumber() + 1);
        pageModel.put("totalPages", bookPage.getTotalPages());

        return pageModel;
    }

    /**
     * Metodo para obtener el siguiente id del libro ingresado, se ordena por bookId desc para obtener
     * el ultimo bookId y sumarle 1, si no hay books se retorna 1 por defecto.
     * @return siguiente bookId que se esta registrando.
     */
    private Long nextBookId(){
        Query query = new Query();
        
        query.with(Sort.by(Sort.Direction.DESC, "bookId"));
        BookModel lastBook = mongoOperations.findOne(query, BookModel.class);

        return lastBook == null ? 1 : lastBook.getBookId() + 1;
    }
}
