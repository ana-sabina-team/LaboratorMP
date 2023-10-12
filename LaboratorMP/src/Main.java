import labMP_Book_Store.main.UI.Console;
import labMP_Book_Store.main.repository.ClientRepository;
import labMP_Book_Store.main.repository.ClientRepositoryImpl;
import labMP_Book_Store.main.service.ClientService;

public class Main {
    public static void main(String[] args) {

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientService(clientRepository);
        Console console = new Console(clientService);

        console.runMenu();

        System.out.println("bye");
    }
}
