package main.service;

import main.domain.Book;
import main.domain.validators.ValidatorException;
import main.repository.BookDatabaseRepository;
import main.repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookService {
    public static final DateTimeFormatter DATE_FORMAT_PUBLICATION_YEAR = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Repository<Long, Book> bookRepository;

    public BookService(Repository<Long, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }
    public void addBook(Book book) throws ValidatorException {
        Optional<Book> bookToVerify = bookRepository.findOne(book.getId());

        if (bookToVerify.isPresent()) {
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

    public List<Book> delete(long id) throws ParserConfigurationException, TransformerException, SAXException {
        bookRepository.delete(id);
        System.out.println("Book deleted successfully!");
        return null;
    }

    public Optional<Book> updateBook(Long id, String title, String author, LocalDate date) {
        Optional<Book> bookToUpdate = bookRepository.findOne(id);
        if (bookToUpdate.isPresent()) {
            bookToUpdate.get().setAuthor(author);
            bookToUpdate.get().setTitle(title);
            bookToUpdate.get().setYearOfPublication(date);
            bookRepository.update(bookToUpdate.get());
            return bookToUpdate;
        }
        return Optional.empty();
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
