package repository.book;

public abstract class BookRepositoryDecorator implements BookRepository{
    protected BookRepository decoratedBookRepository;

    public BookRepositoryDecorator(BookRepository bookRepository) {
        decoratedBookRepository = bookRepository;
    }
}
