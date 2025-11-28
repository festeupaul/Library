package model;

import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private Long bookId;
    private Long customerId;
    private Long employeeID;
    private LocalDateTime saleDate;
    private int quantity;
    private double totalPrice;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getEmployeeID() { return employeeID; }
    public void setEmployeeID(Long employeeID) { this.employeeID = employeeID; }

    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}