package main.service;

import main.domain.Book;
import main.domain.Client;
import main.repository.BookRepositoryImpl;
import main.repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class BookService {


    private Repository<Long, Book> bookRepository;

    public BookService(Repository<Long, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }


    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Set<Book> getAllBooks() {
        Set<Book> books = new HashSet<>();
        bookRepository
                .findAll()
                .forEach(books::add);
        return books;
    }

    public void deleteBook(long id){
        bookRepository.delete(id);
        System.out.println("Book deleted successfully!");
    }

    public void updateBook(Long id,String title,String author) {

        Optional<Book> bookToUpdate = bookRepository.findOne(id);

        Book existingBook = null;
        if (bookToUpdate.isPresent()) {

            existingBook = bookToUpdate.get();
            existingBook.setAuthor(author);
            existingBook.setTitle(title);

        }
        bookRepository.update(existingBook);

    }





    public Set<Book> filterBooksByTitle(String s) {
        Iterable<Book> books = bookRepository.findAll();
        Set<Book> filteredBooks= new HashSet<>();
        for (Book book : books) {
            if (book.getTitle().contains(s)) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }


}
