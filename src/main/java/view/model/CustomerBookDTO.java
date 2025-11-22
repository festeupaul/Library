package view.model;

import javafx.beans.property.*;

public class CustomerBookDTO {
    private StringProperty author;
    private StringProperty title;
    private DoubleProperty price;
    private IntegerProperty stock;

    public void setAuthor(String author) { authorProperty().set(author); }
    public String getAuthor() { return authorProperty().get(); }
    public StringProperty authorProperty() {
        if (author == null) author = new SimpleStringProperty(this, "author");
        return author;
    }

    public void setTitle(String title) { titleProperty().set(title); }
    public String getTitle() { return titleProperty().get(); }
    public StringProperty titleProperty() {
        if (title == null) title = new SimpleStringProperty(this, "title");
        return title;
    }

    public void setPrice(double price) { priceProperty().set(price); }
    public double getPrice() { return priceProperty().get(); }
    public DoubleProperty priceProperty() {
        if (price == null) price = new SimpleDoubleProperty(this, "price");
        return price;
    }

    public void setStock(int stock) { stockProperty().set(stock); }
    public int getStock() { return stockProperty().get(); }
    public IntegerProperty stockProperty() {
        if (stock == null) stock = new SimpleIntegerProperty(this, "stock");
        return stock;
    }
}