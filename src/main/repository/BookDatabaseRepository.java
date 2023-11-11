package main.repository;

import main.domain.Book;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookDatabaseRepository implements Repository<Long, Book> {

    private static final String USER = "postgres";
    private static final String PASSWORD = "veterinara";
    private static final String URL_JDBC = "jdbc:postgresql://localhost:5432/postgres";


    protected Validator<Book> validator;

    public BookDatabaseRepository(Validator<Book> validator) {
        this.validator = validator;
    }


    @Override
    public Optional<Book> findOne(Long aLong) {
        try {
            Connection connection = DriverManager.getConnection(URL_JDBC, USER, PASSWORD);
            String sqlString = "select * from books where (id=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                if (resultSet.getDate("yearofpublication") != null) {
                    book.setYearOfPublication(resultSet.getDate("yearofpublication").toLocalDate());
                }

                connection.close();
                return Optional.of(book);
            } else {
                connection.close();
                return Optional.empty();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Iterable<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL_JDBC, USER, PASSWORD)) {
            String sqlString = "select * from books";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId((long) resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setYearOfPublication(resultSet.getDate("yearofpublication").toLocalDate());
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        try {
            Connection connection = DriverManager.getConnection(URL_JDBC, USER, PASSWORD);
            String sqlString = "insert into books (id, title, author, yearofpublication) values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getAuthor());
            preparedStatement.setDate(4, Date.valueOf(entity.getYearOfPublication()));
            preparedStatement.executeUpdate();
            connection.close();
            return Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Book> delete(Long idToDelete) {
        try {
            Connection connection = DriverManager.getConnection(URL_JDBC, USER, PASSWORD);
            String sqlString = "delete from books where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setLong(1, idToDelete);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {

        String sqlUpdate = "update books set title = ?, author = ?, yearofpublication = ?  where id = ?";
        try (Connection conn = DriverManager.getConnection(URL_JDBC, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdate)
        ) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(entity.getYearOfPublication()));
            preparedStatement.setInt(4, entity.getId().intValue());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}