package ro.ntt.catalog.repository;
import ro.ntt.catalog.model.BaseEntity;
import ro.ntt.catalog.model.Book;

import javax.xml.validation.Validator;
import java.util.Iterator;
import java.util.Optional;


public interface BookRepository<ID,T extends BaseEntity<ID>> {

    Book fiindPme(Long id) ;

    Iterator<Book> findOne();


    Iterable<Book> findAll();




    Optional<T> save(T entity) ;


    Book delete(Long id);


   Optional<T> delete (ID id);


   Optional<T> update (T entity) ;
}
