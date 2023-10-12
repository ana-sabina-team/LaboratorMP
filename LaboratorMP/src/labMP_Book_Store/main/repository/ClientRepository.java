package labMP_Book_Store.main.repository;

import labMP_Book_Store.main.model.Client;

public interface ClientRepository {
    Client findOne(Long id);

    Iterable<Client> findAll();

    Client save(Client entity);

    Client delete(Long id);

    Client update(Client entity);
}
