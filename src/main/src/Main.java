import ro.ntt.catalog.repository.BookRepository;
import ro.ntt.catalog.repository.BookRepositorylmpl;
import ro.ntt.catalog.service.BookService;
import ro.ntt.catalog.ui.Console;

public class Main {
    public static void main(String[] args) {

        BookRepository bookRepository=new BookRepositorylmpl() ;

        BookService bookService=new BookService(bookRepository);

        Console console=new Console(bookService);

        console.runMenu();

        System.out.println(" BYE");





    }
}