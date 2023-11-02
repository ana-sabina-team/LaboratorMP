package main;

import main.UI.Console;
import main.domain.Book;
import main.domain.Client;
import main.domain.validators.BookValidator;
import main.domain.validators.ClientValidator;
import main.domain.validators.Validator;
import main.repository.BookFileRepository;
import main.repository.BookXmlRepository;
import main.repository.ClientFileRepository;
import main.repository.Repository;
import main.service.BookService;
import main.service.ClientService;
import main.repository.BookXmlRepository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static main.repository.BookXmlRepository.loadData;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {

        Validator<Client> clientValidator = new ClientValidator();
        Repository<Long, Client> clientRepository = new ClientFileRepository(clientValidator, "ClientFile");
        ClientService clientService = new ClientService(clientRepository);
        Validator<Book> bookValidator = new BookValidator();
        Repository<Long, Book> bookRepository = new BookFileRepository(bookValidator, "BookFile");
        BookService bookService = new BookService(bookRepository);


        BookXmlRepository bookXmlRepository=new BookXmlRepository();
        Console console = new Console(clientService, bookService,bookXmlRepository);



//        Book book1=new Book(1L,"title1","author1", LocalDate.of(2020,9,9));
//        Book book2=new Book(6L,"title2","author2", LocalDate.of(2021,9,9));
//        Book book3=new Book(9L,"aaaa","aaaaa", LocalDate.of(1990,9,9));

//
//        BookXmlRepository.saveToXml(book1);
//        BookXmlRepository.saveToXml(book2);
//        BookXmlRepository.saveToXml(book3);


        List<Book> books=loadData();
        System.out.println(books);
        console.runMenu();
        System.out.println("bye");
    }
}
