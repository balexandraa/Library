package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import mapper.UserMapper;
import model.Role;
import model.User;
import model.validation.Notification;
import service.book.BookService;
import service.order.OrderService;
import service.report.ReportService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;
import view.model.BookDTO;
import view.model.UserDTO;

import java.util.List;

import static database.Constants.Roles.*;

// OBSERVER
public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;
    private final OrderService orderService;
    private final UserService userService;
    private final ReportService reportService;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService, OrderService orderService, UserService userService, ReportService reportService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.bookService = bookService;
        this.orderService = orderService;

        this.userService = userService;

        this.reportService = reportService;

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
                Role role = loginNotification.getResult().getRoles().get(0);
                switch (role.getRole()) {
                    case EMPLOYEE:
                        List<BookDTO> bookDTOS = BookMapper.convertBookListToBookDTOList(bookService.findAll());
                        List<BookDTO> soldBookDTOS = BookMapper.convertBookListToBookDTOList(orderService.findAllBooks());

                        Notification<User> userNotification = userService.findByUsername(loginNotification.getResult().getUsername());
                        User user = userNotification.getResult();

                        EmployeeView employeeView = new EmployeeView(loginView.getPrimaryStage(), bookDTOS, soldBookDTOS);
                        new EmployeeController(employeeView, bookService, orderService, user);
                        break;

                    case CUSTOMER:
                        loginView.setActionTargetText("LogIn Successfull as Customer!");
                        break;

                    case ADMINISTRATOR:
                        List<UserDTO> userDTOS = UserMapper.convertUserListToUserDTOList(userService.findAll());

                        AdminView adminView = new AdminView(loginView.getPrimaryStage(), userDTOS);
                        new AdminController(adminView, userService, authenticationService, orderService, reportService);
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
            Notification<Boolean> registerNotification = authenticationService.register(username, password, CUSTOMER);

            if (registerNotification.hasError()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successul!");
            }
        }
    }

}
