package main.repository;

import main.domain.Client;
import main.domain.validators.Validator;

public class ClientRepositoryImpl extends InMemoryRepository <Long, Client> {
    public ClientRepositoryImpl(Validator<Client> clientValidator) {
        super(clientValidator);

        // apeleaza constructorul la clasa parinte InMemoryRepository
    }
}

