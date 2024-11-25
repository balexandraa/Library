package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Role;
import model.User;
import model.validation.Notification;
import service.book.BookService;
import service.book.soldBook.SoldBookService;
import service.user.AuthenticationService;
import view.BookView;
import view.LoginView;
import view.model.BookDTO;

import java.util.List;

import static database.Constants.Roles.*;

// OBSERVER
public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;
    private final SoldBookService soldBookService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService, SoldBookService soldBookService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.bookService = bookService;
        this.soldBookService = soldBookService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasError()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
               // loginView.setActionTargetText("LogIn Successfull!");
                Role role = loginNotification.getResult().getRoles().get(0);
                switch (role.getRole()) {
                    case EMPLOYEE:
                        List<BookDTO> bookDTOS = BookMapper.convertBookListToBookDTOList(bookService.findAll());
                        List<BookDTO> soldBookDTOS = BookMapper.convertBookListToBookDTOList(soldBookService.findAll());

                        BookView bookView = new BookView(loginView.getPrimaryStage(), bookDTOS, soldBookDTOS);
                        new BookController(bookView, bookService, soldBookService);

                        //EmployeeView employeeView = new EmployeeView();
                        //new EmployeeController();
                       // BookView bookView = new BookView(loginView.getPrimaryStage(), boo)
                        break;

                    case CUSTOMER:
                        loginView.setActionTargetText("LogIn Successfull as Customer!");
                        break;

                    case ADMINISTRATOR:
                        loginView.setActionTargetText("LogIn Successfull as Administrator!");
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // register se face prin save care returneaza boolean
            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasError()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successul!");
            }
        }
    }

}
