package main.repository;

import main.domain.Client;
import main.domain.validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientRepositoryImpl extends InMemoryRepository <Long, Client> {


    public ClientRepositoryImpl(Validator<Client> clientValidator) {
        super(clientValidator);

        // apeleaza constructorul la clasa parinte InMemoryRepository
    }



}

