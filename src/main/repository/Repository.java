package main.repository;

import main.domain.BaseEntity;
import main.domain.validators.ValidatorException;

import java.util.Optional;

public interface Repository<ID,T extends BaseEntity<ID>> {



    //parintele
    // 2 generics --->id:generic for base entity  property id
    // t ----->ii generic pentru  clasa base entity (T poate fi orice subclasa ce extinde base entity)

    Optional<T> findOne(ID id);

    Iterable<T> findAll();

    Optional<T> save(T entity ) throws ValidatorException;

    Optional<T> delete(ID id);

    Optional<T> update(T entity);

}
