package main;

import main.UI.Console;
import main.domain.Book;
import main.domain.Client;
import main.domain.validators.BookValidator;
import main.domain.validators.ClientValidator;
import main.domain.validators.Validator;
import main.repository.BookFileRepository;
import main.repository.ClientRepositoryImpl;
import main.repository.Repository;
import main.service.BookService;
import main.service.ClientService;

public class Main {
    public static void main(String[] args) {
        Validator<Client> clientValidator = new ClientValidator();
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(clientValidator);
        ClientService clientService = new ClientService(clientRepository);

        BookValidator bookValidator = new BookValidator();
        Repository<Long, Book> bookRepository = new BookFileRepository(bookValidator, "C:\\Users\\Alex\\OneDrive\\Documents\\GitHub\\LaboratorMP\\src\\BookFile");
        BookService bookService = new BookService(bookRepository);

        Console console = new Console(clientService, bookService);

        console.runMenu();

        System.out.println("bye");
    }


}
