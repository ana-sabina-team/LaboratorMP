package main;

import main.UI.Console;
import main.domain.Client;
import main.domain.validators.ClientValidator;
import main.domain.validators.Validator;
import main.repository.ClientFileRepository;
import main.repository.Repository;
import main.repository.ClientRepositoryImpl;
import main.repository.BookRepositoryImpl;
import main.service.BookService;
import main.service.ClientService;

public class Main {
    public static void main(String[] args) {
        Validator<Client> clientValidator = new ClientValidator();
        Repository<Long, Client> clientRepository = new ClientFileRepository(clientValidator, "C:/Users/Sabina/IdeaProjects/LaboratorMP/src/ClientFile");
        ClientService clientService = new ClientService(clientRepository);
        Console console = new Console(clientService);

        console.runMenu();

        System.out.println("bye");
    }
}
