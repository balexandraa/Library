package service.book.soldBook;

import model.Book;

import java.util.List;

public interface SoldBookService {
    boolean sell(Book book);
    List<Book> findAll();
}
