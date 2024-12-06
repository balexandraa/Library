package service.order;

import model.Book;
import model.Order;

import java.util.List;

public interface OrderService {
    boolean sell(Book book, Long userId);
    List<Book> findAllBooks();
    List<Order> findAll();
}
