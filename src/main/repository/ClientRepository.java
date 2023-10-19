package main.repository;

import main.domain.Client;

public interface ClientRepository {
    Client findOne(Long id);

    Iterable<Client> findAll();

    Client save(Client entity);

    Client delete(Long id);

    Client update(Client entity);
}
