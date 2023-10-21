package main.service;


import main.domain.Client;
import main.domain.validators.ValidatorException;
import main.repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private Repository<Long, Client> repository;

    public ClientService(Repository<Long, Client> repository) {
        this.repository = repository;
    }

    public void addClient(Client client) throws ValidatorException {
        Optional<Client> clientToVerify = repository.findOne(client.getId());

        if (clientToVerify.isPresent()){
            throw new ValidatorException("The ID already exists! Try again with another ID!");
        } else {
        repository.save(client);
        }
    }

    public Set<Client> getAllClients() {
        Iterable<Client> clients = repository.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    public void deleteClient(long id){
        Optional<Client> clientToDelete = repository.findOne(id);
        Client existingClient = null;
        if (clientToDelete.isPresent()) {
            existingClient = clientToDelete.get();
        }
        repository.delete(existingClient.getId());
        System.out.println("Client deleted successfully!");
    }

    public void updateClient(Long id,String lastName,String firstName) {

        Optional<Client> clientToUpdate = repository.findOne(id);

       Client existingClient = null;
        if (clientToUpdate.isPresent()) {
            // If the client exists, update its title and author.

            existingClient = clientToUpdate.get();
            existingClient.setLastName(lastName);
            existingClient.setFirstName(firstName);

        }
        // Now, call the update method to save the changes.
        repository.update(existingClient);

    }

    public Set<Client> filterClientsByLastName(String s) {
        Iterable<Client> clients = repository.findAll();
        Set<Client> filteredClients= new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(student -> !student.getLastName().contains(s));

        return filteredClients;
    }
}
