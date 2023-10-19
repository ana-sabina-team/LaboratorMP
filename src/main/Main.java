package main;

import main.UI.Console;
import main.repository.BookRepository;
import main.repository.ClientRepository;
import main.repository.ClientRepositoryImpl;
import main.repository.BookRepositoryImpl;
import main.service.BookService;
import main.service.ClientService;

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepositoryImpl();
        BookService bookService = new BookService(bookRepository);
        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Console console = new Console(clientService,bookService );

        console.runMenu();

        System.out.println("bye");
    }
}
