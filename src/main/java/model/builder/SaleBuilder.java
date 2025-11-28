package model.builder;

import model.Sale;

public class SaleBuilder {
    private Sale sale;

    public SaleBuilder() {
        sale = new Sale();
    }

    public SaleBuilder setId(Long id) {
        sale.setId(id);
        return this;
    }

    public SaleBuilder setBookId(Long bookId) {
        sale.setBookId(bookId);
        return this;
    }

    public SaleBuilder setCustomerId(Long customerId) {
        sale.setCustomerId(customerId);
        return this;
    }

    public SaleBuilder setEmployeeID(Long employeeID) {
        sale.setEmployeeID(employeeID);
        return this;
    }

    public SaleBuilder setSaleDate(java.time.LocalDateTime saleDate) {
        sale.setSaleDate(saleDate);
        return this;
    }

    public SaleBuilder setQuantity(int quantity) {
        sale.setQuantity(quantity);
        return this;
    }

    public SaleBuilder setTotalPrice(double totalPrice) {
        sale.setTotalPrice(totalPrice);
        return this;
    }

    public Sale build() {
        return sale;
    }
}
