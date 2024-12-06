package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.User;
import service.book.BookService;
import service.order.OrderService;
import view.EmployeeView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final BookService bookService;
    private final OrderService orderService;
    private final User user;

    public EmployeeController(EmployeeView employeeView, BookService bookService, OrderService orderService, User user) {
        this.employeeView = employeeView;
        this.bookService = bookService;
        this.orderService = orderService;
        this.user = user;

        this.employeeView.addSaveButtonListener(new SaveButtonListener());
        this.employeeView.addDeleteButtonListener(new DeleteButtonListener());
        this.employeeView.addSellButtonListener(new SellButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent action) {
            String title = employeeView.getTitle();
            String author = employeeView.getAuthor();
            String price = employeeView.getPrice();
            String stock = employeeView.getStock();

            if (title.isEmpty() || author.isEmpty() || price.isEmpty() || stock.isEmpty()) {
                employeeView.addDisplayAlertMessage("Save Error", "Problem at Author or Title or Price or Stock fields", "Can not have an empty Title or Author or Price or Stock field.");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setPrice(Double.parseDouble(price)).setStock(Integer.parseInt(stock)).build();

                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    employeeView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    employeeView.addBookToObservableList(bookDTO);
                } else {
                    employeeView.addDisplayAlertMessage("Save Error", "Problem at adding the book", "There was a problem at adding the book to the database. Please try again!");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) employeeView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                if (deletionSuccessful) {
                    employeeView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    employeeView.removeBookFromObservableList(bookDTO);
                } else {
                    employeeView.addDisplayAlertMessage("Delete Error", "Problem at deleting the book", "There was a problem with the database. Please try again!");
                }
            } else {
                employeeView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) employeeView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {

                boolean sellSuccessful = orderService.sell(BookMapper.convertBookDTOToBook(bookDTO), user.getId());
                if (sellSuccessful) {
                    employeeView.addDisplayAlertMessage("Sell Successful", "Sold Book", "Book was successfully sold.");
                    employeeView.addBookToObserbaleSaleList(bookDTO);

                    bookDTO.setStock(bookDTO.getStock() - 1);

                    if (bookDTO.getStock() == 0) {
                        employeeView.removeBookFromObservableList(bookDTO);
                    }
                }
                else {
                    employeeView.addDisplayAlertMessage("Sell Error", "Problem at selling the book", "There was a problem with the database. Please try again!");
                }
            } else {
                employeeView.addDisplayAlertMessage("Sell Error", "Problem at selling book", "You must select a book before pressing the sell button.");
            }

        }
    }
}
