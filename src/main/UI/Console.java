
package main.UI;

import main.domain.Client;
import main.domain.validators.ValidatorException;
import main.service.ClientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Console {
    private Scanner scanner;
    private ClientService clientService;
    public Console (ClientService clientService) {

        this.clientService = clientService;
    }


    private void printMenu() {
        System.out.println("1 - Client\n" +
                "2 - Book\n" +
                "x - Exit");
    }
        public void runMenu () {
            while (true) {
            this.printMenu();

            Scanner scanner = new Scanner(System.in);
                String option = scanner.next();
                if (option.equals("x")) {
                   break;
                }
                switch (option) {
                    case "1":
                        this.runSubmenuClient();
                        break;
                    case "2":
//                        this.runSubmenuBook();
                        break;
                    default:
                        System.out.println("this option is not yet implemented");
                }
//                printMenu();
            }
        }

    private void runSubmenuClient() {
        while (true) {
            System.out.println("1. Manual add");
            System.out.println("2. To file add");
            System.out.println("3. Print");
            System.out.println("4. Search by last name");
            System.out.println("5. Update client");
            System.out.println("6. Delete client");
            System.out.println("0. Back");

            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.addClientManual();
                    break;
                case "2":
                    this.addClientFile();
                    break;
                case "3":
                    this.printClients();
                    break;
                case "4":
                    this.filterClients();
                    break;
                case "5":
                    this.updateClient();
                    break;
                case "6":
                    this.deleteClient();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

//    private void runSubmenuBook() {
//        while (true) {
//            this.showMenu();
//
//            int option = scanner.nextInt();
//
//            switch (option) {
//                case "1":
//                    this.runSubmenuClient();
//                    break;
//                case "2":
//                    this.runSubmenuBook();
//                    break;
//                case "3":
//                    this.runSubmenuClient();
//                    break;
//                case "4":
//                    this.runSubmenuBook();
//                    break;
//                default:
//                    System.out.println("Invalid option.");
//
//            }
//        }
//    }

        private void printClients () {
            System.out.println("All clients: \n");
            Set<Client> clients = clientService.getAllClients();
            clients.stream().forEach(System.out::println);
        }

        private void addClientManual () {
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


    private void addClientFile() {

            Client client = readClient();
            if (client == null || client.getId() < 0) {
            }
            try {
                clientService.addClient(client);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
    }


    private void filterClients() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Searching for: ");
        String search = scanner.next();
        Set<Client> students = clientService.filterClientsByLastName(search);
        students.stream().forEach(System.out::println);

    }

    public void updateClient() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the ID of the client you want to UPDATE ");
            long idToUpdate= scanner.nextInt();
            System.out.println("Enter the new Last Name");
            String newTitle= scanner.next();
            System.out.println("Enter the new First Name");
            String newAuthor= scanner.next();
            this.clientService.updateClient(idToUpdate,newTitle,newAuthor);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteClient(){

        System.out.println("Enter the ID of the client you want to delete  ");
        Scanner scanner = new Scanner(System.in);
        long id=scanner.nextLong();
        clientService.deleteClient(id);
    }

    private Client readClient() {
        System.out.println("Read client {id, CNP, lastName, firstName, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String CNP = bufferRead.readLine();
            String lastName = bufferRead.readLine();
            String firstName = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());// ...

            Client client = new Client(id, CNP, lastName,firstName, age);
            client.setId(id);

            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}