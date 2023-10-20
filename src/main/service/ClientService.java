package main.service;

import main.domain.Client;
import main.repository.ClientRepositoryImpl;

import java.util.HashSet;
import java.util.Set;

public class ClientService {
    private ClientRepositoryImpl clientRepository;

    public ClientService(ClientRepositoryImpl clientRepository) {

        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {

        clientRepository.save(client);
    }

    public Set<Client> getAllClients() {
        Set<Client> clients = new HashSet<>();
        clientRepository.
                findAll().forEach(clients::add);
        return clients;
    }




}
