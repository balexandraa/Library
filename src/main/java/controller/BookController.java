package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Book;
import service.book.BookService;
import service.book.soldBook.SoldBookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.util.Optional;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    private final SoldBookService soldBookService;

    public BookController(BookView bookView, BookService bookService, SoldBookService soldBookService) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.soldBookService = soldBookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent action) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String price = bookView.getPrice();
            String stock = bookView.getStock();

            if (title.isEmpty() || author.isEmpty() || price.isEmpty() || stock.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author or Title or Price or Stock fields", "Can not have an empty Title or Author or Price or Stock field.");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setPrice(Double.parseDouble(price)).setStock(Integer.parseInt(stock)).build();

                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding the book", "There was a problem at adding the book to the database. Please try again!");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                if (deletionSuccessful) {
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting the book", "There was a problem with the database. Please try again!");
                }
            } else {
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
               // bookView.addDisplayAlertMessage("Not implemented", "Book cannot be sold yet", "You cannot sold a book yet. Retry later!");
//                boolean sellSuccessful = bookService.sell(BookMapper.convertBookDTOToBook(bookDTO));
//                if (sellSuccessful) {
//                    bookView.addDisplayAlertMessage("Sell Successful", "Sold Book", "Book was successfully sold.");
//                    bookDTO.setStock(bookDTO.getStock() - 1);
//
//                    if (bookDTO.getStock() == 0) {
//                        bookView.removeBookFromObservableList(bookDTO);
//                    }
//                }
//                else {
//                    bookView.addDisplayAlertMessage("Sell Error", "Problem at selling the book", "There was a problem with the database. Please try again!");
//                }

                // DE AICI E CU SOLDBOOK SERVICE

//                if (bookDTO.getId() == null || bookDTO.getId() == 0) {
//                    Optional<Book> bookOpt = Optional.ofNullable(bookService.findById(bookDTO.getId()));
//                    if (bookOpt.isPresent()) {
//                        bookDTO.setId(bookOpt.get().getId());
//                    } else {
//                        bookView.addDisplayAlertMessage("Sell Error", "Book not found", "The selected book was not found in the database.");
//                        return;
//                    }
//                }

                boolean sellSuccessful = soldBookService.sell(BookMapper.convertBookDTOToBook(bookDTO));
                if (sellSuccessful) {
                    bookView.addDisplayAlertMessage("Sell Successful", "Sold Book", "Book was successfully sold.");
                    bookView.addBookToObserbaleSaleList(bookDTO);

                    bookDTO.setStock(bookDTO.getStock() - 1);

                    if (bookDTO.getStock() == 0) {
                        bookView.removeBookFromObservableList(bookDTO);
                    }

                }
                else {
                    bookView.addDisplayAlertMessage("Sell Error", "Problem at selling the book", "There was a problem with the database. Please try again!");
                }

                // PANA AICI

            } else {
                bookView.addDisplayAlertMessage("Sell Error", "Problem at selling book", "You must select a book before pressing the sell button.");
            }

        }
    }
}
