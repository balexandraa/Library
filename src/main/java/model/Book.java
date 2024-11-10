package model;

import java.time.LocalDate;

public class Book {
    private Long id;  // Long pt ca daca nu avem id pune null nu 0
    private String author;
    private String title;
    private LocalDate publishedDate;

    // cati constructori faacem si ordinea param??? => BUILDER

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "Book: Id " + id + " Title: " + title + " Author: " + author + " Published Date: " + publishedDate;
    }
}
