package launcher;

import controller.LoginController;
import database.JDBCConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       // ComponentFactory.getInstance(false, primaryStage);

        final Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);

        final LoginView loginView = new LoginView(primaryStage);

        new LoginController(loginView, authenticationService);

    }
}
