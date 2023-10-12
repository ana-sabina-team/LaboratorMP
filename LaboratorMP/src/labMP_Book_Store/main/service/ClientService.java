package labMP_Book_Store.main.service;

import labMP_Book_Store.main.model.Client;
import labMP_Book_Store.main.repository.ClientRepository;

import java.util.HashSet;
import java.util.Set;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {

        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {

        clientRepository.save(client);
    }

    public Set<Client> getAllClients() {
        Set<Client> clients = new HashSet<>();
        clientRepository.findAll().forEach(clients::add);
        return clients;
    }
}
