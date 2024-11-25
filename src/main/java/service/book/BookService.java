package service.book;

// metodele pe care le va putea folosi Presentation

import model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    int getAgeOfBook(Long id);
   // boolean sell(Book book);
    boolean updateStock(String title, String author, int newStock);
    Book findByTitleAndAuthor(String title, String author);
}
