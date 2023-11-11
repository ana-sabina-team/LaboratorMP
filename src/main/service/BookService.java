package main.service;

import main.domain.Book;
import main.domain.validators.ValidatorException;
import main.repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
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

    public void addBook(Book book) throws ValidatorException, ParserConfigurationException, IOException, SAXException {
        Optional<Book> bookToVerify = null;
        try {
            bookToVerify = bookRepository.findOne(book.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (bookToVerify.isPresent()){
            throw new ValidatorException("The ID already exists! Try again with another ID!");
        } else {
            try {
                bookRepository.save(book);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Set<Book> getAllBooks() throws SQLException {
        Set<Book> books = new HashSet<>();
        bookRepository
                .findAll()
                .forEach(books::add);
        return books;
    }

    public void deleteBook(long id) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        bookRepository.delete(id);
        System.out.println("main.domain.Book deleted successfully!");
    }

    public void updateBook(Long id, String title, String author) throws ParserConfigurationException, IOException, TransformerException, SAXException, SQLException {
        Optional<Book> bookToUpdate = bookRepository.findOne(id);
        Book existingBook = null;
        if (bookToUpdate.isPresent()) {
            existingBook = bookToUpdate.get();
            existingBook.setAuthor(author);
            existingBook.setTitle(title);
            bookRepository.update(existingBook);
        } else {
            System.out.println("main.domain.Book with Id " + id + " not exists.");
        }
    }

    public Set<Book> filterBooksByTitle(String s) throws SQLException {
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
