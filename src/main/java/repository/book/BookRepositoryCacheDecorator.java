package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{
    private Cache<Book> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache) {
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<Book> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }

        List<Book> books = decoratedBookRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (cache.hasResult()) {
            return cache.load().stream()
                    .filter(it -> it.equals(id))
                    .findFirst();
        }
        return decoratedBookRepository.findById(id);
    }

    @Override
    public boolean save(Book book) {
        cache.invalidateCache();
        return decoratedBookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        cache.invalidateCache();
        return decoratedBookRepository.delete(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedBookRepository.removeAll();
    }

    @Override
    public boolean updateStock(String title, String author, int newStock) {
        boolean isUpdated = decoratedBookRepository.updateStock(title, author, newStock);

        if (isUpdated) {
            if (cache.hasResult()) {
                List<Book> books = cache.load();
                books.stream()
                        .filter(book -> book.getTitle().equals(title) && book.getAuthor().equals(author))
                        .findFirst()
                        .ifPresent(book -> book.setStock(newStock));

                cache.save(books);
            }
        }
        return isUpdated;
    }

    @Override
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        if (cache.hasResult()) {
            return cache.load().stream()
                    .filter(book -> book.getTitle().equals(title) && book.getAuthor().equals(author))
                    .findFirst();
        }

        Optional<Book> book = decoratedBookRepository.findByTitleAndAuthor(title, author);
        book.ifPresent(b -> {
            List<Book> books = cache.load();
            books.add(b);
            cache.save(books);
        });
        return book;
    }
}
