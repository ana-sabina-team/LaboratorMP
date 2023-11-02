
package main.UI;

import main.domain.Book;
import main.domain.Client;
import main.domain.validators.ValidatorException;
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
import java.util.Scanner;
import java.util.Set;

import static main.service.BookService.DATE_FORMAT_PUBLICATION_YEAR;

public class Console {
    private ClientService clientService;
    private BookService bookService;
    private Scanner scanner;

    private BookXmlRepository bookXmlRepository;

    public Console(ClientService clientService, BookService bookService, BookXmlRepository bookXmlRepository) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
        this.bookXmlRepository=new BookXmlRepository();
    }

    private void printMenu() {
        System.out.println("1 - Client\n" +
                "2 - Book\n" +
                "x - Exit");
    }

    private void printMenuBooks() {
        System.out.println(
                "1 - Add Book\n" +
                "2 - Print all Books from BookFile\n" +
                "3-  Delete a book by ID\n" +
                "4-  Update a book by ID\n" +
                "5-  Filter  a book by title name \n" +
                "6-  Print all books from XML file\n" +
                        "7-  Delete  a books by title in  XML file\n" +

                "0 - Exit");
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
                    this.runSubmenuClient();
                    break;
                case "2":
                    this.runSubmenuBook();
                    break;
                default:
                    System.out.println("this option is not yet implemented");
            }
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

    private void printClients() {
        System.out.println("All clients: \n");
        Set<Client> clients = clientService.getAllClients();
        clients.stream().forEach(System.out::println);
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

    public void updateClient() {
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

    private void deleteClient() {
        System.out.println("Enter the ID of the client you want to delete  ");
        Scanner scanner = new Scanner(System.in);
        long id = scanner.nextLong();
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

            Client client = new Client(id, CNP, lastName, firstName, age);
            client.setId(id);

            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Books
    public void runSubmenuBook() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        while (true) {
            printMenuBooks();
            Integer option = scanner.nextInt();
            switch (option) {
                case 1:
                    addBookFile();
                    break;
                case 2:
                    printBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    filterBooks();
                    break;
                case 6:
                    showBooksFromXML();
                    break;
                case 7:
                    deleteBookFromXMLByTitle();
                    break;
                case 0:
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


    private void addBooK() {
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

    private void showBooksFromXML(){
        try{
           List<Book> books= BookXmlRepository.loadData();
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
        String bookToDelete=scanner.next();

        List<Book> books=BookXmlRepository.deleteFromXmlByBookTitle(bookToDelete);


    }

}