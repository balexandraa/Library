package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.model.UserDTO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.List;

public class AdminView {
    private TableView employeeTableView;
    private final ObservableList<UserDTO> employeesObservableList;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private TextField roleTextField;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label roleLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button generateRaportButton;
    private Text actiontarget;

    public AdminView(Stage primaryStage, List<UserDTO> employees) {
        primaryStage.setTitle("Admin View");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        employeesObservableList = FXCollections.observableArrayList(employees);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        employeeTableView = new TableView<UserDTO>();

        employeeTableView.setPlaceholder(new Label("No employees to display."));

        // data binding
        TableColumn<UserDTO, String> usernameColumn = new TableColumn<UserDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        employeeTableView.getColumns().addAll(usernameColumn);

        employeeTableView.setItems(employeesObservableList);

        gridPane.add(employeeTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3, 1);

        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 4, 1);

        roleLabel = new Label("Role");
        gridPane.add(roleLabel, 1, 2);

        roleTextField = new TextField();
        gridPane.add(roleTextField, 2, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 1, 3);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 2, 3);

        generateRaportButton = new Button("Generate Raport");
        gridPane.add(generateRaportButton, 3, 3);

        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget, 1, 6);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addGenerateRaportButtonListener(EventHandler<ActionEvent> generateRaportButtonListener) {
        generateRaportButton.setOnAction(generateRaportButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public String getRole() {
        return roleTextField.getText();
    }

    public void addUserToObservableList(UserDTO employeeDTO) {
        this.employeesObservableList.add(employeeDTO);
    }

    public void removeUserFromObservableList(UserDTO employeeDTO) {
        this.employeesObservableList.remove(employeeDTO);
    }

    public TableView getEmployeeTableView() {
        return employeeTableView;
    }

    public void setActionTargetText(String text){ this.actiontarget.setText(text);}
}
