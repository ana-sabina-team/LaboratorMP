package main.repository;
import main.domain.Book;

import java.util.Iterator;


public interface BookRepository {

    Book fiindPme(Long id) ;

    Iterator<Book> findOne();
    Iterable<Book> findAll();

    //iterable,interator -interfete , poti utiliza metoda din collection

    Book save(Book entity);

    Book delete(Long id);

    Book update(Book entity) throws Exception;
}
