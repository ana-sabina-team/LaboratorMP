package main.service;

import main.domain.Book;

import main.domain.validators.ValidatorException;
import main.repository.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookService {
    private Repository<Long, Book> repository;

    public BookService(Repository<Long, Book> repository) {

        this.repository = repository;
    }

    public void addBook(Book book) throws ValidatorException {
        repository.save(book);
    }

    public Set<Book> getAllBooks() {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Book> filterBooksByLastName(String s) {
        Iterable<Book> books = repository.findAll();
        Set<Book> filteredBooks= new HashSet<>();
        books.forEach(filteredBooks::add);
        filteredBooks.removeIf(book -> !book.getTitle().contains(s));

        return filteredBooks;
    }
}
