package main.repository;

import main.domain.BaseEntity;
import main.domain.validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, T extends BaseEntity<ID>> {
    Optional<T> findOne(ID id) throws ParserConfigurationException, IOException, SAXException, SQLException;

    Iterable<T> findAll() throws SQLException;

    Optional<T> save(T entity) throws ValidatorException, SQLException;

    Optional<T> delete(ID id) throws ParserConfigurationException, IOException, TransformerException, SAXException;

    Optional<T> update(T entity) throws ValidatorException, ParserConfigurationException, IOException, TransformerException, SAXException;
}
