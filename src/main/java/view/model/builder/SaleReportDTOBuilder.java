package view.model.builder;

import view.model.SaleReportDTO;

public class SaleReportDTOBuilder {
    private SaleReportDTO saleReportDTO;

    public SaleReportDTOBuilder() {
        this.saleReportDTO = new SaleReportDTO();
    }

    public SaleReportDTOBuilder setSaleId(Long saleId) {
        saleReportDTO.setSaleId(saleId);
        return this;
    }

    public SaleReportDTOBuilder setBookTitle(String bookTitle) {
        saleReportDTO.setBookTitle(bookTitle);
        return this;
    }

    public SaleReportDTOBuilder setEmployeeName(String employeeName) {
        saleReportDTO.setEmployeeName(employeeName);
        return this;
    }

    public SaleReportDTOBuilder setCustomerName(String customerName) {
        saleReportDTO.setCustomerName(customerName);
        return this;
    }

    public SaleReportDTOBuilder setQuantity(int quantity) {
        saleReportDTO.setQuantity(quantity);
        return this;
    }

    public SaleReportDTOBuilder setTotalPrice(double totalPrice) {
        saleReportDTO.setTotalPrice(totalPrice);
        return this;
    }

    public SaleReportDTO build() {
        return saleReportDTO;
    }
}
