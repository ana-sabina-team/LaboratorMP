
package main.UI;

import main.domain.Book;
import main.domain.Client;
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
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;

import static main.service.BookService.DATE_FORMAT_PUBLICATION_YEAR;

public class Console (){
    private ClientService clientService;
    private BookService bookService;
    private Scanner scanner;
    private ClientXmlRepository clientXmlRepository;
    private BookXmlRepository bookXmlRepository;


    public Console(ClientService clientService, BookService bookService, BookXmlRepository bookXmlRepository) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
        this.bookXmlRepository = new BookXmlRepository();
        this.clientXmlRepository=new ClientXmlRepository();
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

    private void runSubMenuAddBooks() {
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
    private void runSubMenuDeleteBooks() {
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
    private void runSubMenuUpdateBooks() {
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
    private void runSubMenuAddClients() {
        while (true) {
            System.out.println("1. Manual add");
            System.out.println("2. To file add");
            System.out.println("3. To XML file add");
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
                    this.addClientXML();
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
            System.out.println("1.  Print from file");
            System.out.println("2. Print from XML");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.printClients();
                    break;
                case "2":
                    this.showClientsFromXML();
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
            System.out.println("1.  Print from file");
            System.out.println("3. Print from XML");
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

    private void runSubMenuDeleteClients() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Delete file");
            System.out.println("2. Delete XML");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.deleteClientFromFile();
                    break;
                case "2":
                    this.deleteClientFromXMLByLastName();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }
    private void runSubMenuUpdateClients() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            System.out.println("1. Update file");
            System.out.println("2. Update XML");
            System.out.println("0. Back");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    this.updateClientFromFile();
                    break;
                case "2":
                    this.updateLastNameFromXML();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("this option is not yet implemented");
                    break;
            }
        }
    }


    private void runSubMenuClient() throws ParserConfigurationException, IOException, TransformerException, SAXException {
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
                    this.filterClients();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void printClients() {
        System.out.println("All clients: \n");
        Set<Client> clients = clientService.getAllClients();
        clients.stream().forEach(System.out::println);
    }
    private void showClientsFromXML(){
        try{
            List<Client> clients= ClientXmlRepository.loadData();
            clients.forEach(System.out::println);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void addClientManual() {
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

    private void addClientXML() {

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
        Set<Client> clients = clientService.filterClientsByLastName(search);

        if (clients.isEmpty()) {
            System.out.println("No Client found matching the search criteria.");
        } else {
            System.out.println("Filtered Clients:");

            clients.forEach(client -> System.out.println(client));
        }
    }

    public void updateClientFromFile() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter the ID of the client you want to UPDATE ");
            long idToUpdate = scanner.nextInt();
            System.out.println("Enter the new Last Name");
            String newTitle = scanner.next();
            System.out.println("Enter the new First Name");
            String newAuthor = scanner.next();
            this.clientService.updateClient(idToUpdate, newTitle, newAuthor);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteClientFromFile() {
        System.out.println("Enter the ID of the client you want to delete  ");
        Scanner scanner = new Scanner(System.in);
        long id = scanner.nextLong();
        clientService.deleteClient(id);
    }
    private void deleteClientFromXMLByLastName() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the last name you want to delete ");
        String clientToDelete=scanner.next();

        List<Client> clients=ClientXmlRepository.deleteFromXmlByClientLastName(clientToDelete);


    }

    private List<Client> updateLastNameFromXML() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the last name to update: ");
        String lastNameToUpdate=sc.next();
        System.out.println("enter the new last name : ");
        String newLastName=sc.next();
        List<Client> clients = ClientXmlRepository.updateLastNameInXml(lastNameToUpdate, newLastName);
        return clients;
    }
    private Client readClient() {
        System.out.println("Read client {id, CNP, lastName, firstName, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String CNP = bufferRead.readLine();
            String lastName = bufferRead.readLine();
            String firstName = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());

            Client client = new Client(id, CNP, lastName, firstName, age);
            client.setId(id);

            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Books

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

    private void printBook() {
        System.out.println("All books -->");
        Set<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }


    private void addBook() {
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

    private void deleteBook() {
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
        }
    }

    private void addBookXML(){
        Book book = readBook();
        if (book == null || book.getId() < 0) {
        }
        try {
            bookService.addBook(book);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    private void filterBooks() {
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