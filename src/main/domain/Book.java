package main.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book extends BaseEntity<Long> {
    private String title;

    private String author;

    private Date yearOfPublication;

    public Book(Long id, String title, String author, Date yearOfPublication) {
        super(id);
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
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

    public Date getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Date yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + new SimpleDateFormat("yyyy-MM-dd").format(yearOfPublication) +
                '}';
    }
}