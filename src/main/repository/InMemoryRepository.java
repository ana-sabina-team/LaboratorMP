package main.repository;

import main.domain.BaseEntity;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    public Map<ID, T> entities;

    protected Validator<T> validator;


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
        return entities.values();
    }


    @Override
    public Optional<T> save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent((ID) entity.getId(), entity));
    }


    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable (entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null ");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent((ID) entity.getId(), (k, v) -> entity));
    }
}
