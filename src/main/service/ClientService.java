package main.service;

import main.domain.Client;
import main.domain.validators.ValidatorException;

import main.repository.ClientDatabaseRepository;
import main.repository.Repository;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class ClientService {
    private Repository<Long, Client> clientRepository;

    public ClientService(Repository<Long, Client> clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) throws ValidatorException, ParserConfigurationException, IOException, TransformerException, SAXException {
        Optional<Client> clientToVerify = null;
        try {
            clientToVerify = clientRepository.findOne(client.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (clientToVerify.isPresent()) {
            throw new ValidatorException("The ID already exists! Try again with another ID!");
        } else {
            try {
                clientRepository.save(client);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Set<Client> getAllClients() {
        Set<Client> clients = new HashSet<>();
        try {
            clientRepository.findAll().forEach(clients::add);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    public List<Client> delete(long id) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        clientRepository.delete(id);
        System.out.println("Client deleted successfully!");
        return null;
    }

    public List<Client> updateClient(Long id, String lastName) throws ParserConfigurationException, IOException, SAXException, TransformerException {


        Optional<Client> clientToUpdate = null;
        try {
            clientToUpdate = clientRepository.findOne(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Client existingClient = null;

        if (clientToUpdate.isPresent()) {
            existingClient = clientToUpdate.get();
            existingClient.setLastName(lastName);
        }
        clientRepository.update(existingClient);

        return null;
    }



    public Set<Client> filterClientsByLastName(String s) {
        Iterable<Client> clients = null;
        try {
            clients = clientRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Set<Client> filteredClients = new HashSet<>();
        for (Client client : clients) {
            if (client.getLastName().contains(s)) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }
}
