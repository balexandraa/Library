package view.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SoldBookDTO {
    private StringProperty author;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        if (author == null) {
            author = new SimpleStringProperty(this, "author");  // "author" trb sa corespunda cu numele coloanei din BD
        }
        return author;
    }

    private StringProperty title;

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    private IntegerProperty quantity;

    public void setQuantity(Integer quantity) {
        quantityProperty().set(quantity);
    }

    public Integer getQuantity() {
        return quantityProperty().get();
    }
    public IntegerProperty quantityProperty() {
        if (quantity == null) {
            quantity = new SimpleIntegerProperty(this, "quantity");
        }
        return quantity;
    }
}
