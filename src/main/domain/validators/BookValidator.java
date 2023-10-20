package main.domain.validators;

import main.domain.Book;

import java.util.Calendar;
import java.util.Date;

public class BookValidator implements Validator<Book> {


    @Override
    public void validate(Book entity) throws ValidatorException {

        Date currentDate = new Date();
        Date yearOfPublication = entity.getYearOfPublication();

        Calendar currentYear = Calendar.getInstance();
        currentYear.setTime(currentDate);

        Calendar publicationYear = Calendar.getInstance();
        publicationYear.setTime(yearOfPublication);

        int currentYearValue = currentYear.get(Calendar.YEAR);
        int publicationYearValue = publicationYear.get(Calendar.YEAR);

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
