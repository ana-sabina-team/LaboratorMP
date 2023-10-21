package main.service;

import main.domain.Book;
import main.domain.Client;
import main.domain.validators.ValidatorException;
import main.repository.Repository;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BookService {
    public static final DateTimeFormatter DATE_FORMAT_PUBLICATION_YEAR =   DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Repository<Long, Book> bookRepository;

    public BookService(Repository<Long, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) throws ValidatorException {
        Optional<Book> bookToVerify = bookRepository.findOne(book.getId());

        if (bookToVerify.isPresent()){
            throw new ValidatorException("The ID already exists! Try again with another ID!");
        } else {
            bookRepository.save(book);
        }
    }
    public Set<Book> getAllBooks() {
        Set<Book> books = new HashSet<>();
        bookRepository
                .findAll()
                .forEach(books::add);
        return books;
    }

    public void deleteBook(long id) {
        bookRepository.delete(id);
        System.out.println("Book deleted successfully!");
    }

    public void updateBook(Long id, String title, String author) {
        Optional<Book> bookToUpdate = bookRepository.findOne(id);
        Book existingBook = null;
        if (bookToUpdate.isPresent()) {
            existingBook = bookToUpdate.get();
            existingBook.setAuthor(author);
            existingBook.setTitle(title);
            bookRepository.update(existingBook);
        } else {
            System.out.println("Book with Id " + id + " not exists.");
        }
    }

    public Set<Book> filterBooksByTitle(String s) {
        Iterable<Book> books = bookRepository.findAll();
        Set<Book> filteredBooks = new HashSet<>();
        for (Book book : books) {
            if (book.getTitle().contains(s)) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }
}
