
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static main.service.BookService.DATE_FORMAT_PUBLICATION_YEAR;

public class Console {
    private ClientService clientInMemoryService;
    private ClientService clientFileService;
    private ClientService clientXmlService;
    private ClientService  clientDatabaseService;
    private BookService bookService;

    public Console(ClientService clientInMemoryService, ClientService clientFileService, ClientService clientXmlService, ClientService clientDatabaseService, BookService bookService) {
        this.clientInMemoryService = clientInMemoryService;
        this.clientFileService = clientFileService;
        this.clientXmlService = clientXmlService;
        this.clientDatabaseService = clientDatabaseService;
        this.bookService = bookService;

    }

    private void printMenu() {
        System.out.println("1 - Client\n" +
                "2 - Book\n" +
                "x - Exit");
    }


    public void runMenu() throws ParserConfigurationException, IOException, TransformerException, SAXException, SQLException {
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

    private void runSubMenuAddBooks() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Manual add");
            System.out.println("2. To file add");
            System.out.println("3. To XML file add");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.addBook();
                    break;
                case "2":
                    this.addBookFile();
                    break;
                case "3":
                    this.addBookXML();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }
    private void runSubMenuDeleteBooks() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Delete file");
            System.out.println("2. Delete XML");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.deleteBook();
                    break;
                case "2":
                    this.deleteBookFromXMLByTitle();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }
    private void runSubMenuUpdateBooks() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Update file");
            System.out.println("2. Update XML");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.updateBook();
                    break;
                case "2":
                    this.updateTitleFromXML();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
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
    private void runSubMenuPrintBooks() throws SQLException {
        while (true) {
            System.out.println("1.  Print from file");
            System.out.println("2. Print from XML");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.printBook();
                    break;
                case "2":
                    this.showBooksFromXML();
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

    private void printClients(ClientService clientService) {
        System.out.println("All clients: \n");
        Set<Client> clients = clientService.getAllClients();
        clients.stream().forEach(System.out::println);
    }
//    private void showClientsFromXML(){
//        try {
//            List<Client> clients = ClientXmlRepository.loadData();
//            clients.forEach(System.out::println);
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private void showClientsFromDatabase(){
//        Set<Client> clients = clientService.getAllClients();
//        clients.forEach(System.out::println);
//    }
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


//    private void addClientFile() throws ParserConfigurationException, IOException, SAXException {
//
//        Client client = readClient();
//        if (client == null || client.getId() < 0) {
//        }
//        try {
//            clientService.addClient(client);
//        } catch (ValidatorException e) {
//            e.printStackTrace();
//        } catch (TransformerException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void addClientXML() {
//        Client client = readClient();
//        if (client == null || client.getId() < 0) {
//        }
//        try {
//            clientService.addClient(client);
//        } catch (ValidatorException | ParserConfigurationException | IOException | TransformerException | SAXException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void addClientDatebase() {
//        try {
//            Client client = getClientdata();
//            this.clientService.addClient(client);
//        } catch (InputMismatchException ime) {
//            print("Wrong data type entered!\n");
//            scanner.next();
//        } catch (Exception exception) {
//            print(exception.getMessage());
//            print("\n");
//
//        }
//    }
//    public Client getClientdata() throws IOException {
//        InputStreamReader isr = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(isr);
//
//        print("Enter Client Id: ");
//        Long id = scanner.nextLong();
//        print("Enter Client CNP: ");
//        String CNP = br.readLine();
//        print("Enter Client last name: ");
//        String lastName = br.readLine();
//        print("Enter Client first name: ");
//        String firstName = br.readLine();
//        print("Enter Client age: ");
//        double age = scanner.nextDouble();
//
//
//        return new Client(id, CNP, lastName, firstName, age);
//    }

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
        try {
            try {
                clientService.delete(id);
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

//    private void deleteClientFromXMLByLastName() throws ParserConfigurationException, IOException, SAXException, TransformerException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter the id you want to delete : ");
//        long idToDelete=scanner.nextLong();
//        clientService.delete(idToDelete);
//    }
//
//    private List<Client> updateLastNameFromXML() throws ParserConfigurationException, IOException, SAXException, TransformerException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter the id you want to update: ");
//        Long id=sc.nextLong();
//        System.out.println("enter the new last name ");
//        String lastNameToUpdate = sc.next();
//        List<Client> clients = clientService.updateClient(id,lastNameToUpdate);
//        return clients;
//    }
//    private Client readClient() {
//        System.out.println("Read client {id, CNP, lastName, firstName, age}");
//
//        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            Long id = Long.valueOf(bufferRead.readLine());
//            String CNP = bufferRead.readLine();
//            String lastName = bufferRead.readLine();
//            String firstName = bufferRead.readLine();
//            int age = Integer.parseInt(bufferRead.readLine());
//
//            Client client = new Client(id, CNP, lastName, firstName, age);
//            client.setId(id);
//
//            return client;
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    //Books

    public void runSubMenuBook() throws ParserConfigurationException, IOException, TransformerException, SAXException, SQLException {
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

    private void printBook() throws SQLException {
        System.out.println("All books -->");
        Set<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }


    private void addBook() throws ParserConfigurationException, IOException, SAXException {
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
        bookService.addBook(book);
    }

    public void updateBook() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the ID of the book you want to UPDATE ");
            long idToUpdate = scanner.nextInt();
            System.out.println("Enter the new TITLE");
            String newTitle = scanner.next();
            System.out.println("Enter the new Author");
            String newAuthor = scanner.next();
            this.bookService.updateBook(idToUpdate, newTitle, newAuthor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBook() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        System.out.println("Enter the ID of the book you want to delete  ");
        Scanner scanner = new Scanner(System.in);
        long id = scanner.nextLong();
        bookService.deleteBook(id);
    }

    private Book readBook() {
        System.out.println("Read Book {id, title, author, date}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String title = bufferRead.readLine();
            String author = bufferRead.readLine();
            String releaseDateStr = bufferRead.readLine();

            // Parse the date string into a Date object
            LocalDate releaseDate = null;
            releaseDate = LocalDate.parse(releaseDateStr, DATE_FORMAT_PUBLICATION_YEAR);

            Book book = new Book(id, title, author, releaseDate);
            book.setId(id);

            return book;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void addBookFile() {
        Book book = readBook();
        if (book == null || book.getId() < 0) {
        }
        try {
            bookService.addBook(book);
        } catch (ValidatorException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void addBookXML() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        Book book = readBook();
        if (book == null || book.getId() < 0) {
        }
        try {
            bookService.addBook(book);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    private void filterBooks() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Searching for: ");
        String search = scanner.next();
        Set<Book> books = bookService.filterBooksByTitle(search);

        if (books.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        } else {
            System.out.println("Filtered Books:");

            books.forEach(book -> System.out.println(book));
        }
    }

    private void showBooksFromXML() {
        try {
            List<Book> books = BookXmlRepository.loadData();
            books.forEach(System.out::println);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }


    private void deleteBookFromXMLByTitle() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the title you want to delete ");
        String bookToDelete = scanner.next();

        List<Book> books = BookXmlRepository.deleteFromXmlByBookTitle(bookToDelete);


    }


    private List<Book> updateTitleFromXML() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the title to update: ");
        String titleToUpdate = sc.next();
        System.out.println("enter the new title : ");
        String newTitle = sc.next();
        List<Book> books = BookXmlRepository.updateTitleInXml(titleToUpdate, newTitle);
        return books;

    }


}