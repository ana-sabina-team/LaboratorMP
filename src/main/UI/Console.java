
package main.UI;

import main.domain.Client;
import main.service.ClientService;
import main.domain.Book;
import main.service.BookService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class Console {

    private ClientService clientService;
    private BookService bookService;
    public Console (ClientService clientService, BookService bookService) {
        this.clientService = clientService;
        this.bookService = bookService;
    }
    private void printMenu() {
        System.out.println("1 - Add Client\n" +
                "2 - Add Book\n" +
                "3 - Print all clients\n" +
                "4 - Print all Books\n" +
                "x - Exit");
    }
        public void runMenu () {
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
                        addBooK();
                        break;
                    case "3":
                        printClients();
                        break;
                    case "4":
                        printBook();
                        break;
                    default:
                        System.out.println("this option is not yet implemented");
                }
                printMenu();
            }
        }


        private void printClients () {
            System.out.println("All clients: \n");
            Set<Client> clients = clientService.getAllClients();
            clients.forEach(System.out::println);
        }

        private void addClient () {
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


        private void printBook() {
            System.out.println("All books -->");
            Set<Book> books = bookService.getAllBooks();
            books.forEach(System.out::println);
        }


        private void addBooK () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("id= ");
            long id = scanner.nextLong();

            System.out.println("Title? ");
            String title = scanner.next();

            System.out.println("Author? ");
            String author = scanner.next();


            System.out.println("Date of publication? (format: yyyy-MM-dd)");
            String dateInput = scanner.next();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(dateInput);
                System.out.println("You entered: " + date);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }

            Book book = new Book(id, title, author, date);
            bookService.addBook(book);
        }

    }

