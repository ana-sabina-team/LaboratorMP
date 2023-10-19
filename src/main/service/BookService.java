package main.service;

import main.domain.Book;
import main.repository.BookRepository;

import java.util.HashSet;
import java.util.Set;

public class BookService {


    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Set<Book> getAllBooks() {
        Set<Book> books = new HashSet<>();
        bookRepository
                .findAll() //Read all books from bookRepository
                .forEach(books::add); //For each book in bookRepository we add it to the books Set
        return books;
    }
}