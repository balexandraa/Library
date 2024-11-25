package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

// metodele pe care le poate folosi Service

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    void removeAll();
    boolean updateStock(String title, String author, int newStock);
   // boolean sell(Book book);
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
