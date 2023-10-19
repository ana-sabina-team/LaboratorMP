package main.UI;

import main.domain.Client;
import main.service.ClientService;

import java.util.Scanner;
import java.util.Set;

public class Console {
    private ClientService clientService;

    public Console(ClientService clientService) {
        this.clientService = clientService;
    }

    public void runMenu() {
        printMenu();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String option = scanner.next();
            if (option.equals("x")) {
                break;
            }
            switch (option) {
                case "1":
                    addClient();
                    break;
                case "2":
                    printClients();
                    break;
                default:
                    System.out.println("this option is not yet implemented");
            }
            printMenu();
        }
    }

    private void printClients() {
        System.out.println("All clients: \n");
        Set<Client> clients = clientService.getAllClients();
        clients.forEach(System.out::println);
    }

    private void addClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("id = ");
        Long id = scanner.nextLong();

        System.out.println("CNP = ");
        String CNP = scanner.next();

        System.out.println("Last name = ");
        String lastName = scanner.next();

        System.out.println("First name = ");
        String firstName = scanner.next();

        System.out.println("Age = ");
        int age = scanner.nextInt();

        Client client = new Client(id, CNP, lastName, firstName, age);
        clientService.addClient(client);
    }

    private void printMenu() {
        System.out.println("1 - Add Client\n" +
                "2 - Print all clients\n" +
                "x - Exit");
    }
}
