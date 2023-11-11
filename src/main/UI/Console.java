
package main.UI;

import main.domain.Book;
import main.domain.Client;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;
import main.repository.ClientXmlRepository;
import main.repository.BookXmlRepository;
import main.service.BookService;
import main.service.ClientService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import static main.service.BookService.DATE_FORMAT_PUBLICATION_YEAR;

public class Console {

    private BookService bookInMemoryService;
    private BookService bookXMLService;
    private BookService bookDatabaseService;
    private BookService bookFileService;
    private Scanner scanner;
    private ClientService clientInMemoryService;
    private ClientService clientFileService;
    private ClientService clientXmlService;
    private ClientService  clientDatabaseService;

    public Console(BookService bookInMemoryService, BookService bookXMLService, BookService bookDatabaseService, BookService bookFileService, ClientService clientInMemoryService, ClientService clientFileService, ClientService clientXmlService, ClientService clientDatabaseService) {
        this.bookInMemoryService = bookInMemoryService;
        this.bookXMLService = bookXMLService;
        this.bookDatabaseService = bookDatabaseService;
        this.bookFileService = bookFileService;
        this.clientInMemoryService = clientInMemoryService;
        this.clientFileService = clientFileService;
        this.clientXmlService = clientXmlService;
        this.clientDatabaseService = clientDatabaseService;
    }

    private void printMenu() {
        System.out.println("1 - Client\n" +
                "2 - Book\n" +
                "x - Exit");
    }


    public void runMenu() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            this.printMenu();
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            if (option.equals("x")) {
                break;
            }
            switch (option) {
                case "1":
                    this.runSubMenuClient();
                    break;
                case "2":
                    this.runSubMenuBook();
                    break;
                default:
                    System.out.println("this option is not yet implemented");
            }
        }
    }

    private void runSubMenuClient() {
        while (true) {
            System.out.println("1. Add sub-menu ");
            System.out.println("2. Delete sub-menu");
            System.out.println("3. Update sub-menu");
            System.out.println("4. Print sub-menu");
            System.out.println("5. Filter from file");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.runSubMenuAddClients();
                    break;
                case "2":
                    this.runSubMenuDeleteClients();
                    break;
                case "3":
                    this.runSubMenuUpdateClients();
                    break;
                case "4":
                    this.runSubMenuPrintClients();
                    break;
                case "5":
                    this.filterClients(clientFileService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    private void runSubMenuAddClients()  {
        while (true) {
            System.out.println("1. Manual add");
            System.out.println("2. To file add");
            System.out.println("3. To XML file add");
            System.out.println("4. To Database add");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.addClient(clientInMemoryService);
                    break;
                case "2":
                    this.addClient(clientFileService);
                    break;
                case "3":
                    this.addClient(clientXmlService);
                    break;
                case "4":
                    this.addClient(clientDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }
    private void runSubMenuPrintClients() {
        while (true) {
            System.out.println("1.  Print from memory");
            System.out.println("2.  Print from file");
            System.out.println("3. Print from XML");
            System.out.println("4. Print from Database");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.printClients(clientInMemoryService);
                    break;
                case "2":
                    this.printClients(clientFileService);
                    break;
                case "3":
                    this.printClients(clientXmlService);
                    break;
                case "4":
                    this.printClients(clientDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }
    private void runSubMenuDeleteClients()  {
        while (true) {
            System.out.println("1. Delete memory");
            System.out.println("2. Delete file");
            System.out.println("3. Delete XML");
            System.out.println("4. Delete Database");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.deleteClient(clientInMemoryService);
                    break;
                case "2":
                    this.deleteClient(clientFileService);
                    break;
                case "3":
                    this.deleteClient(clientXmlService);
                    break;
                case "4":
                    this.deleteClient(clientDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }
    private void runSubMenuUpdateClients()  {
        while (true) {
            System.out.println("1. Update memory");
            System.out.println("2. Update file");
            System.out.println("3. Update Xml");
            System.out.println("4. Update Database");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.updateClient(clientInMemoryService);
                    break;
                case "2":
                    this.updateClient(clientFileService);
                    break;
                case "3":
                    this.updateClient(clientXmlService);
                    break;
                case "4":
                    this.updateClient(clientDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }


    private void printClients(ClientService clientService) {
        System.out.println("All clients: \n");
        Set<Client> clients = clientService.getAllClients();
        clients.stream().forEach(System.out::println);
    }

    private void addClient(ClientService clientService){
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
        try {
            clientService.addClient(client);
        } catch (TransformerException | ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }


    private void filterClients(ClientService clientService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Searching for: ");
        String search = scanner.next();
        Set<Client> clients = clientService.filterClientsByLastName(search);

        if (clients.isEmpty()) {
            System.out.println("No Client found matching the search criteria.");
        } else {
            System.out.println("Filtered Clients:");

            clients.forEach(client -> System.out.println(client));
        }
    }

    public void updateClient(ClientService clientService) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the ID of the client you want to UPDATE ");
            long idToUpdate = scanner.nextInt();
            System.out.println("Enter the new Last Name");
            String newLastName = scanner.next();
            clientService.updateClient(idToUpdate, newLastName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteClient(ClientService clientService)  {
        System.out.println("Enter the ID of the client you want to delete  ");
        Scanner scanner = new Scanner(System.in);
        long id = scanner.nextLong();
        clientService.deleteClient(id);
    }
    private void runSubMenuAddBooks() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Manual add");
            System.out.println("2. To file add");
            System.out.println("3. To XML file add");
            System.out.println("4. To DB  add");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.addBook(bookInMemoryService);
                    break;
                case "2":
                    this.addBook(bookFileService);
                    break;
                case "3":
                    this.addBook(bookXMLService);
                    break;
                case "4":
                    this.addBook(bookDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }

    private void runSubMenuDeleteBooks() throws IOException {
        while (true) {
            System.out.println("1. Delete file");
            System.out.println("2. Delete XML");
            System.out.println("3.Delete from DB");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.deleteBook(bookFileService);
                    break;
                case "2":
                    this.deleteBook(bookXMLService);
                    break;
                case "3":
                   this.deleteBook(bookDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }

    private void runSubMenuUpdateBooks() {
        while (true) {
            System.out.println("1. Update file");
            System.out.println("2. Update XML");
            System.out.println("3. Update in DB");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.updateBook(bookFileService);
                    break;
                case "2":
                    this.updateBook(bookXMLService);
                    break;
                case "3":
                    this.updateBook(bookDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }

    private void runSubMenuPrintBooks() {
        while (true) {
            System.out.println("1. Print from file");
            System.out.println("2. Print from XML");
            System.out.println("3. Print from Database");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.printBook(bookFileService);
                    break;
                case "2":
                    this.printBook(bookXMLService);
                    break;
                case "3":
                    this.printBook(bookDatabaseService);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }


    public void runSubMenuBook() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Add menu");
            System.out.println("2. Delete menu");
            System.out.println("3. Update menu");
            System.out.println("4. Print menu");
            System.out.println("5. Filter from file");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            switch (option) {
                case "1":
                    this.runSubMenuAddBooks();
                    break;
                case "2":
                    this.runSubMenuDeleteBooks();
                    break;
                case "3":
                    this.runSubMenuUpdateBooks();
                    break;
                case "4":
                    this.runSubMenuPrintBooks();
                    break;
                case "5":
                    this.filterBooks();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }

    private void printBook(BookService bookService) {
        System.out.println("All books -->");
        Set<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }


    private void addBook(BookService service) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("id= ");
        long id = scanner.nextLong();

        System.out.println("Title? ");
        String title = scanner.next();

        System.out.println("Author? ");
        String author = scanner.next();


        System.out.println("Date of publication? (format: yyyy-MM-dd)");
        String dateInput = scanner.next();

        LocalDate date = null;
        date = LocalDate.parse(dateInput, DATE_FORMAT_PUBLICATION_YEAR);
        System.out.println("You entered: " + date);

        Book book = new Book(id, title, author, date);
        service.addBook(book);
    }

    public void updateBook(BookService service) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the ID of the book you want to UPDATE ");
            long idToUpdate = scanner.nextInt();
            System.out.println("Enter the new TITLE");
            String newTitle = scanner.next();
            System.out.println("Enter the new Author");
            String newAuthor = scanner.next();
            System.out.println("Date of publication? (format: yyyy-MM-dd)");
            String dateInput = scanner.next();

            LocalDate newDate  = LocalDate.parse(dateInput, DATE_FORMAT_PUBLICATION_YEAR);
            service.updateBook(idToUpdate, newTitle, newAuthor, newDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBook(BookService bookService) {
        System.out.println("Enter the ID of the book you want to delete  ");
        Scanner scanner = new Scanner(System.in);
        long id = scanner.nextLong();
        try {
            bookService.delete(id);
        } catch (ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void filterBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Searching for: ");
        String search = scanner.next();
        Set<Book> books = bookDatabaseService.filterBooksByTitle(search);

        if (books.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        } else {
            System.out.println("Filtered Books:");

            books.forEach(book -> System.out.println(book));
        }
    }
}
