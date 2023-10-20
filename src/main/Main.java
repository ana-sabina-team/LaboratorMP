package main;

import main.UI.Console;
import main.domain.Book;
import main.domain.validators.BookValidator;
import main.domain.validators.ClientValidator;
import main.repository.BookFileRepository;
import main.repository.BookRepositoryImpl;
import main.repository.ClientRepositoryImpl;
import main.repository.Repository;
import main.service.BookService;
import main.service.ClientService;

public class Main {
    public static void main(String[] args) {

        BookValidator bookValidator = new BookValidator();
        ClientValidator clientValidator = new ClientValidator();


        Repository<Long, Book> bookRepository = new BookFileRepository(bookValidator, "C:\\Users\\Alex\\OneDrive\\Documents\\GitHub\\LaboratorMP\\src\\BookFile");

        BookService bookService = new BookService(bookRepository);

        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl(clientValidator);

      ClientService clientService = new ClientService(clientRepository);

        Console console = new Console(clientService, bookService);

        console.runMenu();

        System.out.println("bye");
    }


}
