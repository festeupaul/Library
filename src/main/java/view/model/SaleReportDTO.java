package view.model;

public class SaleReportDTO {
    private Long saleId;
    private String bookTitle;
    private String employeeName;
    private String customerName;
    private int quantity;
    private double totalPrice;

    public void setSaleId(Long saleId) { this.saleId = saleId; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public Long getSaleId() { return saleId; }
    public String getBookTitle() { return bookTitle; }
    public String getEmployeeName() { return employeeName; }
    public String getCustomerName() { return customerName; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
}
