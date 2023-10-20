package main.domain.validators;

import main.domain.Book;

import java.time.LocalDate;

public class BookValidator implements Validator<Book> {


    @Override
    public void validate(Book entity) throws ValidatorException {
        LocalDate currentDate = LocalDate.now();
        LocalDate yearOfPublication = entity.getYearOfPublication();

        int currentYearValue = currentDate.getYear();
        int publicationYearValue = yearOfPublication.getYear();

        if (publicationYearValue > currentYearValue) {
            throw new ValidatorException("year of publication cannot be in the future");
        }

        if (entity.getAuthor() == null || entity.getAuthor().trim().length() == 0) {
            throw new ValidatorException("author cannnot be null and empty ");
        }

        if (entity.getTitle() == null || entity.getTitle().trim().length() == 0) {
            throw new ValidatorException("title cannot be null and empty");
        }

    }
}
