package main.repository;

import main.domain.Client;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientFileRepository extends InMemoryRepository<Long, Client> {
    private String fileName;

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.fileName = System.getProperty("user.dir") + "/src/" + fileName;
        loadData();
    }

    @Override
    public Optional<Client> delete(Long id) {
        Optional<Client> deletedClient = null;
        deletedClient = super.delete(id);
        if (deletedClient.isPresent()) {
            updateFile();
        }
        return deletedClient;
    }

    // Add this method to update the file after a client is deleted
    private void updateFile() {
        Path path = Paths.get(fileName);
        List<String> lines = new ArrayList<>();

        entities.values().forEach(client -> {
            String line = client.getId() + "," + client.getCNP() + "," + client.getLastName() + ","
                    + client.getFirstName() + "," + client.getAge();
            lines.add(line);
        });

        try {
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Path path = Paths.get(fileName);

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
                String CNP = items.get(1);
                String lastName = items.get((2));
                String firstName = items.get((3));
                int age = Integer.parseInt(items.get(4));

                Client client = new Client(id, CNP, lastName, firstName, age);
                client.setId(id);

                try {
                    super.save(client);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        validator.validate(entity);
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getCNP() + "," + entity.getLastName() + ","  + entity.getFirstName() + ","+ entity.getAge());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
