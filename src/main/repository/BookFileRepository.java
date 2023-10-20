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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookFileRepository extends InMemoryRepository<Long, Book>      {

    private String filename;


    public BookFileRepository(Validator<Book> validator , String filename) {
        super(validator);
        this.filename=filename;

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
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date releaseDate = null;


                try {
                    releaseDate = dateFormat.parse(releaseDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle date parsing error as needed
                }

                Book book = new Book(id, title, author,releaseDate);
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
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }


    private void saveToFile(Book entity) {
        Path path = Paths.get(filename);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getTitle() + "," + entity.getAuthor() + "," + entity.getYearOfPublication());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    }



