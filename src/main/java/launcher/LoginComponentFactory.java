package launcher;

import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.book.soldBook.SoldBookRepository;
import repository.book.soldBook.SoldBookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.book.soldBook.SoldBookService;
import service.book.soldBook.SoldBookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;

public class LoginComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository bookRepository;

    private final BookService bookService;
    private final SoldBookService soldBookService;
    private final SoldBookRepository soldBookRepository;

    private static LoginComponentFactory instance;

    public static synchronized LoginComponentFactory getInstance(Boolean componentsForTests, Stage stage) {
        if (instance == null) {
            instance = new LoginComponentFactory(componentsForTests, stage);
        }

        return instance;
    }

    public LoginComponentFactory(Boolean componentsForTests, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();

        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);

        this.bookRepository = new BookRepositoryMySQL(connection);
        this.soldBookRepository = new SoldBookRepositoryMySQL(connection);

        this.bookService = new BookServiceImpl(bookRepository);
        this.soldBookService = new SoldBookServiceImpl(bookService, soldBookRepository);

        this.loginView = new LoginView(stage);
        this.loginController = new LoginController(loginView, authenticationService, bookService, soldBookService);

    }

    public LoginView getLoginView() {
        return loginView;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }
}
