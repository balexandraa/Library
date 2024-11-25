package service.book;

import model.Book;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d was not found.".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        return bookRepository.delete(book);
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);
        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }

//    @Override
//    public boolean sell(Book book) {
//        if (book.getStock() > 0) {
//            int newStock = book.getStock() - 1;
//            book.setStock(newStock);
//
//            boolean updatedSoldBook = bookRepository.sell(book);
//
//            if (book.getStock() == 0) {
//                return bookRepository.delete(book) && updatedSoldBook;
//            } else {
//                return bookRepository.updateStock(book.getId(), newStock) && updatedSoldBook;
//              //  return bookRepository.sell(book);
//            }
//        }
//        return false;
//    }

    @Override
    public boolean updateStock(String title, String author, int newStock) {
        return bookRepository.updateStock(title, author, newStock);
    }

    @Override
    public Book findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author)
                .orElseThrow(() -> new IllegalArgumentException("Book with title: %s and author: %s was not found.".formatted(title).formatted(author)));
    }
}

