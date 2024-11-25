package repository.book.soldBook;

import model.Book;

import java.util.List;

public interface SoldBookRepository {
    boolean save(Book book);
    List<Book> findAll();
}
