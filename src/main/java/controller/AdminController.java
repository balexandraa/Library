package controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.UserMapper;
import model.Order;
import model.validation.Notification;
import service.order.OrderService;
import service.report.ReportService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminController {
    private final AdminView adminView;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;
    private final ReportService reportService;

    public AdminController(AdminView adminView, UserService userService, AuthenticationService authenticationService, OrderService orderService, ReportService reportService) {
        this.adminView = adminView;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.orderService = orderService;
        this.reportService = reportService;

        this.adminView.addSaveButtonListener(new SaveButtonListener());
        this.adminView.addDeleteButtonListener(new DeleteButtonListener());
        this.adminView.addGenerateRaportButtonListener(new GenerateRaportButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            String role = adminView.getRole();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                adminView.addDisplayAlertMessage("Save Error", "Problem at Username or Password or Role fileds", "Can not have empty Username or Password or Role field.");
            } else {
                Notification<Boolean> registerNotification = authenticationService.register(username, password, role);

                UserDTO userDTO = new UserDTOBuilder().setUsername(username).build();

                if (registerNotification.hasError()) {
                    adminView.setActionTargetText(registerNotification.getFormattedErrors());
                } else {
                    adminView.addDisplayAlertMessage("Save Succesfull", "User Added", "User was successfully added to the database.");
                    adminView.addUserToObservableList(userDTO);
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            UserDTO userDTO = (UserDTO) adminView.getEmployeeTableView().getSelectionModel().getSelectedItem();
            if (userDTO != null) {
                boolean deletionSuccessful = userService.delete(UserMapper.covertUserDTOToUser(userDTO));

                if (deletionSuccessful) {
                    adminView.addDisplayAlertMessage("Delete Successful", "User Deleted", "User was successfully deleted from the database.");
                    adminView.removeUserFromObservableList(userDTO);
                } else {
                    adminView.addDisplayAlertMessage("Delete Error", "Problem at deleting the user", "There was a problem with the database. Please try again!");
                }
            } else {
                adminView.addDisplayAlertMessage("Delete Error", "Problem at deleting the user", "You must select a user before pressing the delete button.");
            }
        }
    }

    private class GenerateRaportButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            List<Order> orders = orderService.findAll();

            if (orders.isEmpty()) {
                adminView.addDisplayAlertMessage("Empty Report", "No orders found", "There are no orders to report.");
            }

            String dest = "C:\\Users\\alexa\\Documents\\FACULTATE\\AN3\\sem1\\IS\\Library\\OrderReport.pdf";
            try {
                reportService.generateReport(orders, dest);
                adminView.addDisplayAlertMessage("Success", "PDF Raport Generated", "The PDF Raport was generated successfully!");
            } catch (FileNotFoundException e) {
                adminView.addDisplayAlertMessage("Error", "Raport Generation Failed", "There was a problem with generating the report. Please try again!");
            }

        }
    }


}
