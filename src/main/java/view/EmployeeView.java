package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.BookDTO;


import java.util.List;

public class EmployeeView {
    private TableView bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private TableView soldBookTableView;
    private final ObservableList<BookDTO> soldBooksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField priceTextField;
    private TextField stockTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label priceLabel;
    private Label stockLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button sellButton;



    public EmployeeView(Stage primaryStage, List<BookDTO> books, List<BookDTO> soldBookDTOS) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);

        soldBooksObservableList = FXCollections.observableArrayList(soldBookDTOS);
        initSoldBooksTableView(gridPane);

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
        bookTableView = new TableView<BookDTO>();

        bookTableView.setPlaceholder(new javafx.scene.control.Label("No books to display."));

        // Data Bindig intre coloana de Title si obiectul title din DTO
        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");  // numele coloanei efective
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));   // numele din BookDTO

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<BookDTO, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

//        TableColumn<BookDTO, Long> idColumn = new TableColumn<>("Id");
//        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
//        idColumn.setVisible(false);

        // adaugam coloanele in tabelul nostru
        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn);

        // orice modificare in lista Observer va modifica si tabelul
        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 5, 1);

    }

    private void initSoldBooksTableView(GridPane gridPane) {
        soldBookTableView = new TableView<BookDTO>();

        TableColumn<BookDTO, String> titleColumn = new TableColumn<>("Title");  // numele coloanei efective
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<>("Author");  // numele coloanei efective
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

//        TableColumn<BookDTO, Integer> quantityColumn = new TableColumn<>("Quantity");
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        soldBookTableView.getColumns().addAll(titleColumn, authorColumn);

        soldBookTableView.setItems(soldBooksObservableList);

        gridPane.add(soldBookTableView, 5, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        // adaugam label pt stock si price
        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 1, 2);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 2, 2);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 3, 2);

        stockTextField = new TextField();
        gridPane.add(stockTextField, 4, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 1, 3);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 2, 3);

        sellButton = new Button("Sell");
        gridPane.add(sellButton, 3, 3);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener) {
        sellButton.setOnAction(sellButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public String getPrice() {
        return priceTextField.getText();
    }

    public String getStock() {
        return stockTextField.getText();
    }

    public void addBookToObservableList(BookDTO bookDTO) {
        this.booksObservableList.add(bookDTO);
    }

    public void addBookToObserbaleSaleList(BookDTO bookDTO) {
        this.soldBooksObservableList.add(bookDTO);
    }

    public ObservableList<BookDTO> getSoldBooksObservableList() {
        return soldBooksObservableList;
    }

    public void removeBookFromObservableList(BookDTO bookDTO) {
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView() {
        return bookTableView;
    }
}
