package main.repository;

import main.domain.Book;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static main.service.BookService.DATE_FORMAT_PUBLICATION_YEAR;

public class BookFileRepository extends InMemoryRepository<Long, Book> {
    private String filename;

    public BookFileRepository(Validator<Book> validator, String filename) {
        super(validator);
        this.filename = System.getProperty("user.dir") + "/src/" + filename;
        loadData();
    }

    private void loadData() {

        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            Files.lines(path).forEach(line -> {

                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String title = items.get(1);
                String author = items.get((2));


                String releaseDateStr = items.get(3);
                LocalDate releaseDate = LocalDate.parse(releaseDateStr, DATE_FORMAT_PUBLICATION_YEAR);

                Book book = new Book(id, title, author, releaseDate);
                book.setId(id);

                try {
                    super.save(book);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        saveToFile(entity);
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }


    public void saveToFile(Book entity) {
        Path path = Paths.get(filename);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getTitle() + "," + entity.getAuthor() + "," +
                            DATE_FORMAT_PUBLICATION_YEAR.format(entity.getYearOfPublication()));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



