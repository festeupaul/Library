package view.model.builder;

import view.model.EmployeeBookDTO;

public class EmployeeBookDTOBuilder {
    private EmployeeBookDTO employeeBookDTO;
    public EmployeeBookDTOBuilder() {
        employeeBookDTO = new EmployeeBookDTO();
    }

    public EmployeeBookDTOBuilder setId(Long id) {
        employeeBookDTO.setId(id);
        return this;
    }

    public EmployeeBookDTOBuilder setAuthor(String author) {
        employeeBookDTO.setAuthor(author);
        return this;
    }

    public EmployeeBookDTOBuilder setTitle(String title) {
        employeeBookDTO.setTitle(title);
        return this;
    }

    public EmployeeBookDTOBuilder setId(Double price) {
        employeeBookDTO.setPrice(price);
        return this;
    }

    public EmployeeBookDTOBuilder setPrice(Double price) {
        employeeBookDTO.setPrice(price);
        return this;
    }

    public EmployeeBookDTOBuilder setStock(Integer stock) {
        employeeBookDTO.setStock(stock);
        return this;
    }

    public EmployeeBookDTO build() {
        return employeeBookDTO;
    }
}
