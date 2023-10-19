package main.repository;

import main.domain.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository{
    private Map<Long, Client> entities;


    public ClientRepositoryImpl() {
        this.entities = new HashMap<>();
    }

    @Override
    public Client findOne(Long id) {
        throw new RuntimeException("not yet implemented");
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
        return clients;
    }

    @Override
    public Client save(Client entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        return entities.putIfAbsent(entity.getId(), entity);
    }

    @Override
    public Client delete(Long id) {
        throw new RuntimeException("not yet implemented");
    }

    @Override
    public Client update(Client entity) {
        throw new RuntimeException("not yet implemented");
    }
}

