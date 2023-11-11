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
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {
        Validator<Client> clientValidator = new ClientValidator();

      ClientService clientInMemoryService = new ClientService(new ClientRepositoryImpl(clientValidator));
      ClientService clientFileService = new ClientService(new ClientFileRepository(clientValidator, "ClientFile"));
      ClientService clientXmlService = new ClientService(new ClientXmlRepository(clientValidator));
      ClientService clientDatabaseService = new ClientService(new ClientDatabaseRepository(clientValidator));

      Validator<Book> bookValidator = new BookValidator();
      Repository<Long, Book> bookRepository = new BookXmlRepository(bookValidator);
      BookService bookService = new BookService(bookRepository);

      Console console = new Console(clientInMemoryService, clientFileService, clientXmlService, clientDatabaseService, bookService);

      console.runMenu();
      System.out.println("bye ^_^ thank you >(*_*)<");
    }
}
