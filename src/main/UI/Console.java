package src.main.ro.ntt.catalog.UI;

import src.main.ro.ntt.catalog.domain.Book;
import src.main.ro.ntt.catalog.service.BookService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

public class Console {

    private BookService bookService;

    public Console(BookService bookService) {
        this.bookService = bookService;
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
                    addBooK();
                    break;
                case "2":
                    printBook();
                    break;
                default:
                    System.out.println("this option is not yet implemented");
            }
            printMenu();
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


    private void printMenu() {
        System.out.println("1 - Add Book\n" +
                "2 - Print all Books\n" +
                "x - Exit");
    }


}
