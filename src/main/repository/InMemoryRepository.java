package main.repository;

import main.domain.BaseEntity;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    public Map<ID, T> entities;

    private Validator<T> validator;


    public InMemoryRepository(Validator<T> validator) {
        entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));

    }

    @Override
    public Iterable<T> findAll() {

        // entities.entrySet().stream()
        //Returneaza un stream de elemente, peste care se poate itera element cu element
        //-returneaza un stream aplicand functia ->
        // .map(element -> element.getValue()).collect(Collectors.toSet());

        return entities.values();
    }


    @Override
    public Optional<T> save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

    }


    @Override
    public Optional<T> delete(ID id) {

        if (id == null) {
            throw new IllegalArgumentException("id must not be null");

            //Optional <T> optionalT=delete(id)
            //optionalT.get() --------------->return the original cat object . getAge()
            //optionalT.isPresent () -------->check if it is a value in the optional
            //optionalT.orElse() ------------>if it's empty it will return what it is in the brackets
            //optionalT.orElseThrow() ------->if empty it will throw no such element exception

        }
        return Optional.ofNullable //we have to put the result in the optional box firs
                (entities.remove(id));//entities->the thing we want to create an optional of
    }


    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null ");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

}
