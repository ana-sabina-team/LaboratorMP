package main.domain;

import java.time.LocalDate;

import static main.service.BookService.DATE_FORMAT_PUBLICATION_YEAR;

public class Book extends BaseEntity<Long> {
    private String title;

    private String author;

    private LocalDate yearOfPublication;



    public Book(Long id, String title, String author, LocalDate yearOfPublication) {
        super(id);
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public Book() {
        super();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(LocalDate yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID='" + getId() + '\'' +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + (yearOfPublication != null ?  DATE_FORMAT_PUBLICATION_YEAR.format(yearOfPublication) : "") +
                '}';
    }
}