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
import java.util.List;


public class Main {


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Validator<Client> clientValidator = new ClientValidator();
        Repository<Long, Client> clientRepository = new ClientFileRepository(clientValidator, "ClientFile");
        ClientService clientService = new ClientService(clientRepository);


        Validator<Book> bookValidator = new BookValidator();

        BookService bookInMemoryService = new BookService(new BookRepositoryImpl(bookValidator));
        BookService bookDatabaseService = new BookService(new BookDatabaseRepository(bookValidator));
        BookService bookXMLService = new BookService(new BookXmlRepository(bookValidator));
        BookService bookFileService = new BookService(new BookFileRepository(bookValidator, "BookFile"));

        Console console = new Console(clientService, bookInMemoryService, bookDatabaseService, bookXMLService, bookFileService);
        console.runMenu();

        System.out.println("bye ^_^ thank you >(*_*)<");
    }


}
