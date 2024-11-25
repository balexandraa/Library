package model;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private Long id;  // Long pt ca daca nu avem id pune null nu 0
    private String author;
    private String title;
    private LocalDate publishedDate;
    // add price and stock for SALE
    private double price;
    private int stock;

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

    // add get and set for price and stock


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // refactor toString to have price and stock

    @Override
    public String toString() {
        return "Book: Id " + id + " Title: " + title + " Author: " + author + " Published Date: " + publishedDate
                + " Stock: " + stock + " Price: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(author, book.author) && Objects.equals(title, book.title) && Objects.equals(publishedDate, book.publishedDate);
    }

    // si aici cred ca trb sa adaug stock si price
    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, publishedDate);
    }
}
