package view.model.builder;

import view.model.CustomerBookDTO;

public class CustomerBookDTOBuilder {
    private CustomerBookDTO customerBookDTO;

    public CustomerBookDTOBuilder() {
        customerBookDTO = new CustomerBookDTO();
    }

    public CustomerBookDTOBuilder setAuthor(String author) {
        customerBookDTO.setAuthor(author);
        return this;
    }

    public CustomerBookDTOBuilder setTitle(String title) {
        customerBookDTO.setTitle(title);
        return this;
    }

    public CustomerBookDTOBuilder setPrice(double price) {
        customerBookDTO.setPrice(price);
        return this;
    }

    public CustomerBookDTOBuilder setStock(int stock) {
        customerBookDTO.setStock(stock);
        return this;
    }

    public CustomerBookDTO build() {
        return customerBookDTO;
    }
}