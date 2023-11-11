package main;

import main.UI.Console;
import main.domain.Book;
import main.domain.Client;
import main.domain.validators.BookValidator;
import main.domain.validators.ClientValidator;
import main.domain.validators.Validator;
import main.repository.*;
import main.service.BookService;
import main.service.ClientService;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;


public class Main {


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Validator<Client> clientValidator = new ClientValidator();

        ClientService clientInMemoryService = new ClientService(new ClientRepositoryImpl(clientValidator));
        ClientService clientFileService = new ClientService(new ClientFileRepository(clientValidator, "ClientFile"));
        ClientService clientXmlService = new ClientService(new ClientXmlRepository(clientValidator));
        ClientService clientDatabaseService = new ClientService(new ClientDatabaseRepository(clientValidator));

        Validator<Book> bookValidator = new BookValidator();

        BookService bookInMemoryService = new BookService(new BookRepositoryImpl(bookValidator));
        BookService bookDatabaseService = new BookService(new BookDatabaseRepository(bookValidator));
        BookService bookXMLService = new BookService(new BookXmlRepository(bookValidator));
        BookService bookFileService = new BookService(new BookFileRepository(bookValidator, "BookFile"));






        Console console = new Console(bookInMemoryService, bookXMLService,  bookDatabaseService,  bookFileService,  clientInMemoryService,  clientFileService,  clientXmlService,  clientDatabaseService);
        console.runMenu();

        System.out.println("bye ^_^ thank you >(*_*)<");
    }


}
