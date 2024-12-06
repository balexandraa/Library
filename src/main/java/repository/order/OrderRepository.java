package repository.order;

import model.Book;
import model.Order;

import java.util.List;

public interface OrderRepository {
    boolean save(Book book, Long userId);
    List<Book> findAllBooks();
    List<Order> findAll();
}
