
package main.UI;

import main.domain.Client;
import main.domain.validators.ValidatorException;
import main.service.ClientService;
import main.domain.Book;
import main.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class Console {

    private ClientService clientService;
    private BookService bookService;

    private Scanner scanner;


    public Console (ClientService clientService, BookService bookService) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }
    private void printMenu() {
        System.out.println("1 - Add Client\n" +
                "2 - Add Book\n" +
                "3 - Print all clients\n" +
                "4 - Print all Books\n" +
                "5-  Delete a book by ID\n"+
                "6-  Update a book by ID\n"+
                "7-  Filter  a book by title name \n"+

                "-1 - Exit");
    }
        public void runMenu () {
            while (true) {
                printMenu();
                Integer option = scanner.nextInt();
                if (option.equals(-1)) {
                    break;
                }
                switch (option) {
                    case 1:
                        addClient();
                        break;
                    case 2:
                        addBooK();
                        break;
                    case 3:
                        printClients();
                        break;
                    case 4:
                        printBook();
                        break;
                    case 5 :
                        deleteBook();
                        break;
                    case 6 :
                        updateBook();
                        break;
                    case 7 :
                        filterBooks();
                    case 0:
                        return;

                        default:
                        System.out.println("this option is not yet implemented");


                }
            }
        }

    public void updateBook() {
        try {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the ID of the book you want to UPDATE ");
        long idToUpdate= scanner.nextInt();
        System.out.println("Enter the new TITLE");
        String newTitle= scanner.next();
        System.out.println("Enter the new Author");
        String newAuthor= scanner.next();
        this.bookService.updateBook(idToUpdate,newTitle,newAuthor);

    }catch (Exception e) {
        throw new RuntimeException(e);
        }
    }


    public void printClients () {
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


        private void deleteBook(){

            System.out.println("Enter the ID of the book you want to delete  ");
            Scanner scanner = new Scanner(System.in);
           long id=scanner.nextLong();
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = null;
            try {
                releaseDate = dateFormat.parse(releaseDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

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



    private void filterBooks(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Searching for: ");
        String search = scanner.next();
        Set<Book> books=bookService.filterBooksByTitle(search);

        if (books.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        } else {
            System.out.println("Filtered Books:");

            books.forEach(book -> System.out.println(book));
        }


        }




    }



