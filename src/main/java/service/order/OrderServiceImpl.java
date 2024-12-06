package service.order;

import model.Book;
import model.Order;
import repository.order.OrderRepository;
import service.book.BookService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final BookService bookService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(BookService bookService, OrderRepository soldBookRepository) {
        this.bookService = bookService;
        this.orderRepository = soldBookRepository;
    }
    @Override
    public boolean sell(Book book, Long userId) {
        if (book.getStock() > 0) {
            int newStock = book.getStock() - 1;
            book.setStock(newStock);

            // salvam in order
            boolean isSoldBookSaved = orderRepository.save(book, userId);

            if (newStock == 0) {
                return isSoldBookSaved && bookService.delete(book);
            } else {
                return isSoldBookSaved && bookService.updateStock(book.getTitle(), book.getAuthor(), newStock);
            }
        }
        return false;
    }

    @Override
    public List<Book> findAllBooks() {
        return orderRepository.findAllBooks() ;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
