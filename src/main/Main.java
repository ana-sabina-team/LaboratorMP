package main;

import main.UI.Console;
import main.repository.ClientRepository;
import main.repository.ClientRepositoryImpl;
import main.service.ClientService;

public class Main {
    public static void main(String[] args) {

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Console console = new Console(clientService);

        console.runMenu();

        System.out.println("bye");
    }
}
