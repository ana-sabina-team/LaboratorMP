package src.main.ro.ntt.catalog.repository;
import src.main.ro.ntt.catalog.domain.Book;

import java.util.*;


public class BookRepositorylmpl implements BookRepository{


    private Map<Long, Book> entities;
    /*id=book*/


    //constructor:The constructor initializes the entities field as a new instance of HashMap.
    // This means the repository starts with an empty collection of students.
    public BookRepositorylmpl(){
        this.entities=new HashMap<>();
    }


    @Override
    public Book fiindPme(Long id) {
        return null;
    }

    @Override
    public Iterator<Book> findOne() {
        return null;
    }

    @Override
   public Iterable<Book> findAll(){
        return entities.values();
    }


    @Override
    public Book save(Book entity) {
        Optional<Book> existing = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        return existing.orElse(null);
    }


    @Override
    public Book delete(Long id) {
        entities.remove(id);
        return null;
    }


    @Override
    public Book update(Book entity) throws Exception {
        if (entities.containsKey(entity.getId())) {
            throw new Exception("there is no entity with the id  " + entity.getId());
        }
            entities.put(entity.getId(),entity);
         return entity;
        }







    }





