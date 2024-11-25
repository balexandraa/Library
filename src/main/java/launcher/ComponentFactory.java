package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.Book;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.book.soldBook.SoldBookRepository;
import repository.book.soldBook.SoldBookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.book.soldBook.SoldBookService;
import service.book.soldBook.SoldBookServiceImpl;
import view.BookView;
import view.model.BookDTO;
import view.model.SoldBookDTO;

import java.sql.Connection;
import java.util.List;

// Singleton class
public class ComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final SoldBookRepository soldBookRepository;
    private final SoldBookService soldBookService;
    private static ComponentFactory instance;

    //private ComponentFactory() {}

    public static synchronized ComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTest, primaryStage);
        }
        return instance;
    }

    public ComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);

        this.soldBookRepository = new SoldBookRepositoryMySQL(connection);
        this.soldBookService = new SoldBookServiceImpl(bookService, soldBookRepository);

        // luam toate cartile din BD si le afisam in interfata -> de la inceput
        List<BookDTO> bookDTOS = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        List<BookDTO> soldBookDTOS = BookMapper.convertBookListToBookDTOList(soldBookService.findAll());
        this.bookView = new BookView(primaryStage, bookDTOS, soldBookDTOS);
        this.bookController = new BookController(bookView, bookService, soldBookService);
    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}
