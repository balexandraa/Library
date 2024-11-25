package service.book.soldBook;

import model.Book;
import repository.book.soldBook.SoldBookRepository;
import service.book.BookService;

import java.util.List;

public class SoldBookServiceImpl implements SoldBookService{
    private final BookService bookService;
    private final SoldBookRepository soldBookRepository;

    public SoldBookServiceImpl(BookService bookService, SoldBookRepository soldBookRepository) {
        this.bookService = bookService;
        this.soldBookRepository = soldBookRepository;
    }
    @Override
    public boolean sell(Book book) {
        if (book.getStock() > 0) {
            int newStock = book.getStock() - 1;
            book.setStock(newStock);

            // salvam in sold_book
            boolean isSoldBookSaved = soldBookRepository.save(book);

            if (newStock == 0) {
                return isSoldBookSaved && bookService.delete(book);
            } else {
                return isSoldBookSaved && bookService.updateStock(book.getTitle(), book.getAuthor(), newStock);
            }
        }
        return false;
    }

    @Override
    public List<Book> findAll() {
        return soldBookRepository.findAll() ;
    }
}
