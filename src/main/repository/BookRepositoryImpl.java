package main.repository;

import main.domain.Book;
import main.domain.validators.Validator;


public class BookRepositoryImpl extends InMemoryRepository<Long, Book> {
    //constructor:The constructor initializes the entities field as a new instance of HashMap.
    // This means the repository starts with an empty collection of students.
    public BookRepositoryImpl(Validator<Book> bookValidator) {
        super(bookValidator); // apeleaza constructorul la clasa parinte InMemoryRepository
    }
}





