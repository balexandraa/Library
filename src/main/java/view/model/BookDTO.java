package view.model;


import javafx.beans.property.*;

public class BookDTO {
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

    // adaugam price
    private DoubleProperty price;
    public void setPrice(Double price) {
        priceProperty().set(price);
    }

    public Double getPrice() {
        return priceProperty().get();
    }

    public DoubleProperty priceProperty() {
        if (price == null) {
            price = new SimpleDoubleProperty(this, "price");
        }
        return price;
    }

    //adaugam stock
    private IntegerProperty stock;

    public void setStock(Integer stock) {
        stockProperty().set(stock);
    }

    public Integer getStock() {
        return stockProperty().get();
    }
    public IntegerProperty stockProperty() {
        if (stock == null) {
            stock = new SimpleIntegerProperty(this, "stock");
        }
        return stock;
    }

//    private IntegerProperty quantity;
//
//    public void setQuantity(Integer quantity) {
//        quantityProperty().set(quantity);
//    }
//
//    public Integer getQuantity() {
//        return quantityProperty().get();
//    }
//    public IntegerProperty quantityProperty() {
//        if (quantity == null) {
//            quantity = new SimpleIntegerProperty(this, "quantity");
//        }
//        return quantity;
//    }

//    private LongProperty id;
//    public void setId(Long id) {
//        idProperty().set(id);
//    }
//
//    public Long getId() {
//        return idProperty().get();
//    }
//
//    public LongProperty idProperty() {
//        if (id == null) {
//            id = new SimpleLongProperty(this, "id");
//        }
//        return id;
//    }

}
