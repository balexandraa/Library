package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{
    private final List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }
    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean updateStock(String title, String author, int newStock) {
        Optional<Book> book = findByTitleAndAuthor(title, author);
        if (book.isPresent()) {
            book.get().setStock(newStock);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return books.parallelStream()
                .filter(book -> book.getTitle().equals(title) && book.getAuthor().equals(author))
                .findFirst();
    }

}
