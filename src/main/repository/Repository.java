package main.repository;

import main.domain.BaseEntity;
import main.domain.Client;
import main.domain.validators.ValidatorException;

import java.util.Optional;

public interface ClientRepository <ID, T extends BaseEntity<ID>>{
    Optional<T> findOne(ID id);

    Iterable<T> findAll();

    Optional<T> save(T entity) throws ValidatorException;

    Optional<T> delete(ID id);

    Optional<T> update(T entity) throws ValidatorException;
}
