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
import main.repository.ClientXmlRepository;
import main.repository.Repository;
import main.service.BookService;
import main.service.ClientService;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Validator<Client> clientValidator = new ClientValidator();
        Repository<Long, Client> clientRepository = new ClientXmlRepository(clientValidator);
        ClientService clientService = new ClientService(clientRepository);

        Validator<Book> bookValidator = new BookValidator();
        Repository<Long, Book> bookRepository = new BookXmlRepository(bookValidator);
        BookService bookService = new BookService(bookRepository);

        Console console = new Console(clientService, bookService);

        console.runMenu();

        System.out.println("bye");
    }
}
